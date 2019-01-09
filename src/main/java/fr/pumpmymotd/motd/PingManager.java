package fr.pumpmymotd.motd;

import java.io.File;
import java.util.HashMap;

import fr.pumpmymotd.config.ConfigUtils;
import fr.pumpmymotd.motd.Ping.PingBuilder;
import net.md_5.bungee.api.config.ListenerInfo;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.YamlConfiguration;

public class PingManager {
	
	private HashMap<String, Ping> pings;
	private Ping defaultPing;
	private ConfigUtils config;

	public PingManager(ConfigUtils config) {
		
		this.pings = new HashMap<String, Ping>();
		this.config = config;
		
		this.defaultPing = null;
		
	}

	public void load() throws Exception {
			
		Configuration config = this.config.getConfiguration("plugin.yml");
			
		this.defaultPing = new Ping.PingBuilder().build();
		
		for (File file : this.config.getDataFolder().listFiles()) {
			
			
			
		}
		
	}

	public HashMap<String, Ping> getPings() {
		return pings;
	}

	public ConfigUtils getConfig() {
		return config;
	}
	
	public Ping getDefaultPing() {
		return defaultPing;
	}

}
