package fr.pumpmymotd.motd;

import java.util.HashMap;

import fr.pumpmymotd.config.ConfigUtils;
import net.md_5.bungee.config.Configuration;

public class PingManager {
	
	private HashMap<String, Ping> pings;
	private ConfigUtils config;

	public PingManager(ConfigUtils config) {
		
		this.pings = new HashMap<String, Ping>();
		this.config = config;
		
	}

	public HashMap<String, Ping> getPings() {
		return pings;
	}

	public ConfigUtils getConfig() {
		return config;
	}

}
