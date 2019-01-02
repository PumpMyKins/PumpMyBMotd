package fr.pumpmymotd;

import fr.pumpmymotd.motd.PingManager;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ProxyPingListener implements Listener {
	
	private MotdManager manager;

	public MotdManager getManager() {
		return manager;
	}

	public ProxyPingListener(MotdManager manager) {
		this.manager = manager;
	}
	
	@EventHandler
	public void onProxyPing(ProxyPingEvent event) {
		
		
		
	}
	
}
