package fr.pumpmybmotd.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import fr.pumpmybmotd.MainMotd;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class ConfigUtils {
	
	private MainMotd main;	
	
	public MainMotd getMain() {
		return this.main;
	}

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
			try (InputStream in = this.getResourceAsStream(fileName)){
				Files.copy(in, file.toPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return file;
	}
	
	public InputStream getResourceAsStream(String name) {
		return this.main.getResourceAsStream(name);
	}
	
	public File getDataFolder() {
		return this.main.getDataFolder();
	}
	
	public Configuration getConfiguration(File f) throws Exception {		
		try {
			return ConfigurationProvider.getProvider(YamlConfiguration.class).load(f);
		} catch (Exception e) {
			throw new Exception( f.getName() + " impossible de récupérer la configuration" );
		}		
	}
	
}