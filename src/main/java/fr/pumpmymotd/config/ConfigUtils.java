package fr.pumpmymotd.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import fr.pumpmymotd.MainMotd;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class ConfigUtils {
	
	private MainMotd main;	

	public ConfigUtils(MainMotd main) {
		this.main = main;
	}

	public void initDataFolder() {
		
		if(!main.getDataFolder().exists()) {
			main.getDataFolder().mkdir();
		}
		
	}
	
	public File initAndGetFile(String fileName) {
		
		File file = new File(this.main.getDataFolder(),fileName);
		
		if(!file.exists()) {
			try (InputStream in = this.main.getResourceAsStream(fileName)){
				Files.copy(in, file.toPath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return file;
	}
	
	public File getDataFolder() {
		return this.main.getDataFolder();
	}
	
	public Configuration getConfiguration(String fileName) throws Exception {
		
		File file = new File(this.main.getDataFolder(),fileName);
		
		try {
			return ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
		} catch (Exception e) {
			throw new Exception( fileName + " impossible de récupérer la configuration" );
		}		
	}
	
}