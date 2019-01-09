package fr.pumpmymotd;

import fr.pumpmymotd.motd.Ping;
import fr.pumpmymotd.motd.PingManager;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ProxyPingListener implements Listener {
	
	private PingManager manager;

	public PingManager getManager() {
		return manager;
	}

	public ProxyPingListener(PingManager manager) {
		this.manager = manager;
	}
	
	@EventHandler
	public void onProxyPing(ProxyPingEvent event) {
		
		
		System.out.println(event.getConnection().getListener().getHost());
		System.out.println(event.getConnection().getAddress().getHostString());
		
		String host = "host";
		
		//Ping ping = this.manager.getPing(host);
		
		
	}
	
}
