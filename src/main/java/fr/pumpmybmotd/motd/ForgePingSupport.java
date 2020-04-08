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

		MainMotd mainMotd = this.manager.getConfig().getMain();
		mainMotd.getProxy().getScheduler().schedule(mainMotd, new Runnable() {
			
			@Override
			public void run() {
				for (String host : forges.keySet()) {
					refreshHostModList(host);
				}
				
			}
		}, 0, 5, TimeUnit.MINUTES);
		
	}

	public void addHost(String host) {
		if(!this.forges.containsKey(host)) {
			this.forges.put(host, new ArrayList<ModItem>());
			this.refreshHostModList(host);
		}else {
			System.out.println("ForgePingSupport : " + host + "already in ForgePing support list !");
		}		
	}

	public void clear() {
		this.forges.clear();

	}

	private void refreshHostModList(String host) {

		ServerInfo info = this.manager.getConfig().getMain().getServerInfoWithForcedHost(host);

		if(info == null) {
			System.out.println("ForgePingSupport : " + host + " server not found !");
			return;
		}

		info.ping(new Callback<ServerPing>() {
			
			@Override
			public void done(ServerPing result, Throwable error) {
				if(error == null && result != null) {
					System.out.println("ForgePingSupport : " + host + " ping callback : " + result.getModinfo().getModList().size() + " mods found !");
					forges.replace(host, result.getModinfo().getModList());
				}else {
					System.out.println("ForgePingSupport : " + host + " ping error !");
					forges.replace(host, new ArrayList<ModItem>());
				}
			}
		});
	}

	public List<ModItem> getModList(String host) {
		if(this.forges.containsKey(host)) {
			return this.forges.get(host);
		}
		System.out.println("ForgePingSupport : " + host + "no found !");
		return new ArrayList<ModItem>();
	}

}
