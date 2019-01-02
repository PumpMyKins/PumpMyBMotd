package fr.pumpmymotd;

import fr.pumpmymotd.config.ConfigUtils;
import fr.pumpmymotd.motd.MotdManager;
import net.md_5.bungee.api.plugin.Plugin;

public class MainMotd extends Plugin{

	@Override
	public void onEnable() {
		
		getProxy().getPluginManager().registerListener(this, new ProxyPingListener(null));
		
	}
	
}
