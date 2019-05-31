package fr.pumpmymotd;

import java.net.InetSocketAddress;
import java.util.ArrayList;

import fr.pumpmymotd.motd.Ping;
import fr.pumpmymotd.motd.PingManager;
import net.md_5.bungee.api.Callback;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.ServerPing.ModInfo;
import net.md_5.bungee.api.ServerPing.Protocol;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
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
		
		InetSocketAddress inet = con.getVirtualHost();
		if(inet == null) return;
		String host = inet.getHostString();
		Ping ping = this.manager.getPing(host);
		ServerPing serverPing = event.getResponse();
		
		if(serverPing == null) return;
		
		serverPing.setDescriptionComponent(new TextComponent(ping.getLine1().replace("&", "§") + "\n" + ping.getLine2().replace("&", "§")));		

		if(ping.getFavicon() != null) {
			
			serverPing.setFavicon(ping.getFavicon());
			
		}
		
		if(ping.hasFmlSupport() || ping.hasCheckDispo()) {
			
			ServerInfo serverInfo = this.manager.getServerByForcedHost(host);
			if(serverInfo != null) {
				
				serverInfo.ping(new Callback<ServerPing>() {
					
					@Override
					public void done(ServerPing result, Throwable error) {
						
						if(ping.hasFmlSupport()) {
							
							ModInfo modInfo = serverPing.getModinfo();
							modInfo.setType("FML");
							
							if(result == null || error != null) {
								
								error.printStackTrace();
								modInfo.setModList(new ArrayList<>());
								event.setResponse(serverPing);
								return;
								
							}		
							
							modInfo.setModList(result.getModinfo().getModList());
							
						}
						
						if(ping.hasCheckDispo()) {
							
							if(result == null || error != null) {
								
								serverPing.setVersion(new Protocol("Server Rebooting", -1));
								
							}
							
						}
						
						event.setResponse(serverPing);
						
					}
				});
				
			}
			
		}else {
			
			event.setResponse(serverPing);	
			
		}
					
		
	}
	
}
