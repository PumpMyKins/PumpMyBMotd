package fr.pumpmymotd;

import fr.pumpmymotd.config.ConfigUtils;
import fr.pumpmymotd.motd.MotdManager;
import net.md_5.bungee.api.plugin.Plugin;

public class MainMotd extends Plugin{

	private MotdManager manager;
	
	@Override
	public void onEnable() {
		
		ConfigUtils config = ConfigUtils.getConfig(this);
		config.initDataFolder();
		
		MotdManager manager = new MotdManager(config);
		
		getProxy().getPluginManager().registerListener(this, new ProxyPingListener(null));
		
	}
	
}
