package fr.pumpmymotd;

import fr.pumpmymotd.motd.Ping;
import fr.pumpmymotd.motd.PingManager;
import net.md_5.bungee.api.AbstractReconnectHandler;
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
		
		System.out.println(AbstractReconnectHandler.getForcedHost(event.getConnection()));
		
		
	}
	
}
