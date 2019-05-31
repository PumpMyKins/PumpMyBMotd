package fr.pumpmymotd.motd;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

import fr.pumpmymotd.config.ConfigUtils;
import fr.pumpmymotd.motd.Ping.PingBuilder;
import net.md_5.bungee.api.Favicon;
import net.md_5.bungee.api.config.ListenerInfo;
import net.md_5.bungee.api.config.ServerInfo;
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
	public List<String> getForcedHost(){

		List<String> list = new ArrayList<String>();

		for (ListenerInfo listener : this.config.getMain().getProxy().getConfig().getListeners()) {

			for (String host : listener.getForcedHosts().keySet()) {

				list.add(host);

			}

		}

		return list;


	}
	
	@SuppressWarnings("deprecation")
	public ServerInfo getServerByForcedHost(String host){

		if(host == null || host.isEmpty()) return null;
		
		for (ListenerInfo listener : this.config.getMain().getProxy().getConfig().getListeners()) {

			for (Entry<String, String> forcedHost : listener.getForcedHosts().entrySet()) {

				if(forcedHost.getKey().equalsIgnoreCase(host)) {
					
					return this.config.getMain().getProxy().getServerInfo(forcedHost.getValue());
					
				}

			}

		}

		return null;


	}
	
	

	public void load() {

		File faviconFile = new File(this.config.getDataFolder(),PingConstant.FAVICONS_FOLDER_NAME);
		faviconFile.mkdir();

		try {
			this.createDefaultMtdFile();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}		

		try {
			this.defaultPing = this.getPingFromFile(new File(this.config.getDataFolder(),PingConstant.DEFAULT_MOTD_FILE_NAME));
		} catch (Exception e) {
			e.printStackTrace();
			PingBuilder ping = new PingBuilder();
			ping.setLine1("None");
			ping.setLine2("None");
			ping.setFavicon(null);
			ping.setFmlSupport(false);
			ping.setCheckDisponibility(false);
			this.defaultPing = ping.build();
		}


		for (String host : this.getForcedHost()) {
			
			File file = new File(this.config.getDataFolder(), host + ".yml");

			if(file.exists()) {

				try {
					this.initMotdFileConfiguration(file);
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}

				try {
					this.addHostPing(host, this.getPingFromFile(file));
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}

			}
			
		}

	}

	private Ping getPingFromFile(File f) throws Exception{

		Configuration config = this.config.getConfiguration(f);
		PingBuilder builder = new PingBuilder();

		System.out.println(f.getName());
		
		if(config.contains("line1") && !config.getString("line1").isEmpty()) {

			String line1 = config.getString("line1");

			if(line1.equals("default")) {
				if(f.getName().equals("default.yml")) {
					throw new Exception("Invalide Value for default configuration file !");
				}
				builder.setLine1(this.defaultPing.getLine1());

			}else {

				builder.setLine1(line1);

			}

		}else {

			builder.setLine1(this.defaultPing.getLine1());

		}

		if(config.contains("line2") && !config.getString("line2").isEmpty()) {

			String line2 = config.getString("line2");

			if(line2.equals("default")) {
				if(f.getName().equals("default.yml")) {
					throw new Exception("Invalide Value for default configuration file !");
				}
				builder.setLine2(this.defaultPing.getLine2());

			}else {

				builder.setLine2(line2);

			}

		}else {

			builder.setLine2(this.defaultPing.getLine2());

		}
		
		if(!config.contains("check.disponibility")) {

			builder.setCheckDisponibility(config.getBoolean("check.disponibility"));

		}
		
		if(!config.contains("check.fml")) {

			builder.setFmlSupport(config.getBoolean("check.fml"));

		}

		if(config.contains("favicon") && !config.getString("favicon").isEmpty()) {

			String faviconName = config.getString("favicon");
			if(faviconName.equals("default") || faviconName.equals("None")) {				
				if(f.getName().equals("default.yml")) {
					throw new Exception("Invalide Value for default configuration file !");
				}

				builder.setFavicon(this.defaultPing.getFavicon());

			}else {

				try {
					BufferedImage img = ImageIO.read(new File(this.config.getDataFolder(),PingConstant.FAVICONS_FOLDER_NAME + File.separator + config.getString("favicon")));
					Favicon fav = Favicon.create(img);					
					builder.setFavicon(fav);
				} catch (Exception e) {
					throw new Exception("UnFounded Favicon !!!!");
				}
				

			}			

		}else {

			builder.setFavicon(this.defaultPing.getFavicon());

		}

		return builder.build();

	}

	private void createDefaultMtdFile() throws Exception {

		File defaultFile = new File(this.config.getDataFolder(),PingConstant.DEFAULT_MOTD_FILE_NAME);
		defaultFile.createNewFile();

		this.initMotdFileConfiguration(defaultFile);

	}

	public void initMotdFileConfiguration(File f) throws Exception {

		Configuration conf = YamlConfiguration.getProvider(YamlConfiguration.class).load(f);
		String value = "default";
		if(f.getName().equals("default.yml")) {
			value = "None";
		}

		if(!conf.contains("line1")) {

			conf.set("line1", value);

		}

		if(!conf.contains("line2")) {

			conf.set("line2", value);

		}

		if(!conf.contains("favicon")) {

			conf.set("favicon", value);

		}

		if(!conf.contains("check.disponibility")) {

			conf.set("check.disponibility", false);

		}
		
		if(!conf.contains("check.fml")) {

			conf.set("check.fml", false);

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

	public void clearPings() {
		this.pings.clear();
	}

	public ConfigUtils getConfig() {
		return config;
	}

	public Ping getDefaultPing() {
		return defaultPing;
	}

}
