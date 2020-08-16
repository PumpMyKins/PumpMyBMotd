package fr.pumpmybmotd.motd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import fr.pumpmybmotd.MainMotd;
import net.md_5.bungee.api.Callback;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.ServerPing.ModItem;
import net.md_5.bungee.api.config.ServerInfo;

public class ForgePingSupport {

	private HashMap<String, List<ModItem>> forges;

	private PingManager manager;

	public ForgePingSupport(PingManager pingManager) {
		this.manager = pingManager;
		this.forges = new HashMap<String, List<ModItem>>();		
	}
	
	public void refreshAll() {
		for (String host : forges.keySet()) {
			refreshHostModList(host);
		}
	}

	public void addHost(String host) {
		if(!this.forges.containsKey(host)) {
			this.forges.put(host, new ArrayList<ModItem>());
			System.out.println("ForgePingSupport : " + host + " added in ForgePing support list !");
			this.refreshHostModList(host);
		}else {
			System.out.println("ForgePingSupport : " + host + " already in ForgePing support list !");
		}		
	}

	public void clear() {
		this.forges.clear();

	}

	private void refreshHostModList(String host) {
		this.refreshHostModList(host,0);
	}
	
	private void refreshHostModList(String host, int i) {

		ServerInfo info = this.manager.getConfig().getMain().getServerInfoWithForcedHost(host);

		if(info == null) {
			System.out.println("ForgePingSupport : " + host + " server not found !");
			return;
		}

		final int newI = i*2 + 1;
		if(newI > 60) {
			System.out.println("ForgePingSupport : " + host + " skip ping (iter>60) !");
			return;
		}
		
		info.ping(new Callback<ServerPing>() {
			
			@Override
			public void done(ServerPing result, Throwable error) {
				if(error == null && result != null) {
					System.out.println("ForgePingSupport : " + host + " ping callback : " + result.getModinfo().getModList().size() + " mods found !");
					forges.replace(host, result.getModinfo().getModList());
				}else {
					System.out.println("ForgePingSupport : " + host + " ping error ! Schedule another test in 2 minutes");
					forges.replace(host, new ArrayList<ModItem>());
					MainMotd mainMotd = manager.getConfig().getMain();
					mainMotd.getProxy().getScheduler().schedule(mainMotd, new Runnable() {
						
						@Override
						public void run() {
							refreshHostModList(host,newI);							
						}
						
					}, newI, TimeUnit.MINUTES);
				}
			}
		});
	}

	public List<ModItem> getModList(String host) {
		if(this.forges.containsKey(host)) {
			return this.forges.get(host);
		}
		System.out.println("ForgePingSupport : " + host + " not found !");
		return new ArrayList<ModItem>();
	}

}
