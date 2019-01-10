package fr.pumpmymotd.motd;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;

import javax.imageio.ImageIO;

import fr.pumpmymotd.config.ConfigUtils;
import fr.pumpmymotd.motd.Ping.PingBuilder;
import net.md_5.bungee.api.Favicon;
import net.md_5.bungee.api.config.ListenerInfo;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.YamlConfiguration;

public class PingManager {
	
	private HashMap<String, Ping> pings;
	private Ping defaultPing;
	private ConfigUtils config;

	public static class PingConstant {
		
		public final static String DEFAULT_MOTD_FILE_NAME = "default.yml";
		public final static String FAVICONS_FOLDER_NAME = "favicons";
		
	}
	
	public PingManager(ConfigUtils config) {
		
		this.pings = new HashMap<String, Ping>();
		this.config = config;
		
		this.defaultPing = null;
		
	}
	
	public Ping getPing(String host) {
		
		if(host != null & this.pings.containsKey(host)) {
			
			return this.pings.get(host);
			
		}else {
			
			return this.defaultPing;
			
		}
		
	}

	@SuppressWarnings("deprecation")
	public void load() throws Exception {
		
		File faviconFile = new File(this.config.getDataFolder(),PingConstant.FAVICONS_FOLDER_NAME);
		faviconFile.mkdir();
		
		this.createDefaultMtdFile();			
		this.defaultPing = this.getPingFromFile(new File(this.config.getDataFolder(),PingConstant.DEFAULT_MOTD_FILE_NAME));
		
		for (ListenerInfo listener : this.config.getMain().getProxy().getConfig().getListeners()) {
			
			for (String host : listener.getForcedHosts().keySet()) {
				
				File file = new File(this.config.getDataFolder(), host + ".yml");
				file.createNewFile();	
				
				this.initMotdFileConfiguration(file);
				this.addHostPing(host, this.getPingFromFile(file));
				
			}
			
		}	
		
	}
	
	private Ping getPingFromFile(File f) throws Exception {
		
		Configuration config = this.config.getConfiguration(f);
		PingBuilder builder = new PingBuilder();
		
		builder.setLine1(config.getString("line1"));
		builder.setLine2(config.getString("line2"));
		
		builder.setFavicon(new File(this.config.getDataFolder(),PingConstant.FAVICONS_FOLDER_NAME + File.separator + config.getString("favicon")));
			
		return builder.build();
		
	}
	
	private void createDefaultMtdFile() throws Exception {
		
		File defaultFile = new File(this.config.getDataFolder(),PingConstant.DEFAULT_MOTD_FILE_NAME);
		defaultFile.createNewFile();
		
		this.initMotdFileConfiguration(defaultFile);
		
	}
	
	private void initMotdFileConfiguration(File f) throws Exception {

		Configuration conf = YamlConfiguration.getProvider(YamlConfiguration.class).load(f);
		
		if(!conf.contains("line1")) {
			
			conf.set("line1", new String());
			
		}
		
		if(!conf.contains("line2")) {
			
			conf.set("line2", new String());
			
			
			conf.set("favicon", new String());
			
		}
		
		YamlConfiguration.getProvider(YamlConfiguration.class).save(conf, f);
		
	}
	
	public void addHostPing(String host, Ping ping) {
		
		if(!this.pings.containsKey(host)) {
			
			System.out.println("Host Motd added : " + host);
			
			this.pings.put(host, ping);
		}else {
			
			System.err.println("Host Motd duplication : " + host);
			
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
