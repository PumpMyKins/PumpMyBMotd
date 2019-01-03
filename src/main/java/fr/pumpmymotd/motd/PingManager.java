package fr.pumpmymotd.motd;

import java.io.File;
import java.util.HashMap;

import fr.pumpmymotd.config.ConfigUtils;
import net.md_5.bungee.config.Configuration;

public class PingManager {
	
	private HashMap<String, Ping> pings;
	private Ping defaultPing;
	private ConfigUtils config;

	public PingManager(ConfigUtils config) {
		
		this.pings = new HashMap<String, Ping>();
		this.config = config;
		
		this.defaultPing = null;
		
	}

	public HashMap<String, Ping> getPings() {
		return pings;
	}

	public ConfigUtils getConfig() {
		return config;
	}

	public void load() throws Exception {
			
		Configuration config = this.config.getConfiguration("plugin.yml");
			
		Ping ping = new Ping.PingBuilder().build();
		
	}

}
