package fr.pumpmymotd;

import java.util.ArrayList;

import fr.pumpmymotd.motd.Ping;
import fr.pumpmymotd.motd.PingManager;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.PendingConnection;
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
	public void onProxyPing(ProxyPingEvent event){
		
		PendingConnection con = event.getConnection();
		if(con == null)
			return;
		
		String host = con.getVirtualHost().getHostString();
		System.out.println(host);
		Ping ping = this.manager.getPing(host);
		
		ServerPing serverPing = event.getResponse();
		
		if(serverPing == null) return;
		
		serverPing.setDescriptionComponent(new TextComponent(ping.getLine1().replace("&", "§") + "\n" + ping.getLine2().replace("&", "§")));		
		serverPing.getModinfo().setModList(new ArrayList<ServerPing.ModItem>());	
		
		if(ping.getFavicon() != null) {
			
			serverPing.setFavicon(ping.getFavicon());
			
		}
		
		event.setResponse(serverPing);				
		
	}
	
}
