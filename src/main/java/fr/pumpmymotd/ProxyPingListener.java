package fr.pumpmymotd;

import java.net.InetSocketAddress;
import fr.pumpmymotd.motd.Ping;
import fr.pumpmymotd.motd.PingManager;
import net.md_5.bungee.api.Callback;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.ServerPing.ModInfo;
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
	public void onProxyPing(ProxyPingEvent event) throws InterruptedException{

		PendingConnection con = event.getConnection();
		if(con == null)
			return;

		InetSocketAddress inet = con.getVirtualHost();
		if(inet == null) return;
		String host = inet.getHostString();
		Ping ping = this.manager.getPing(host);
		ServerPing serverPing = event.getResponse();
		ServerPing newServerPing = new ServerPing();

		if(serverPing == null) return;

		newServerPing.setVersion(serverPing.getVersion());
		newServerPing.setPlayers(serverPing.getPlayers());

		ModInfo modInfo = newServerPing.getModinfo();
		if(ping.hasFmlSupport()) {
			modInfo.setType("FML");				
		}else {
			modInfo.setType("VANILLA");
		}

		newServerPing.setDescriptionComponent(new TextComponent(ping.getLine1().replace("&", "§") + "\n" + ping.getLine2().replace("&", "§")));		

		if(ping.getFavicon() != null) {

			newServerPing.setFavicon(ping.getFavicon());

		}
		
		// pumpmyconnect integration
		/*if(ping.hasCheckDispo()) {
			
			

		}else {

			event.setResponse(newServerPing);	

		}*/
		event.setResponse(newServerPing);

	}

}
