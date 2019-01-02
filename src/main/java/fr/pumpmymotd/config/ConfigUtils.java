package fr.pumpmymotd.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import fr.pumpmymotd.MainMotd;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

/** 
 * Classe de methods utiles pour l'utilisation des configurations BungeeCord
 * @author Clem-Fern
 */

public class ConfigUtils {

	private static ConfigUtils config = new ConfigUtils(); // variable instancier de la class ConfigUtils par defaut
	private static MainMotd main;	// variable contenant une instance courante du Main
	
	/**
	 * 
	 * @param m MainBungeeMotd / instance de la class principale
	 * @return Instance de ConfigUtils initialisé au paramètre MainBungeeMotd donné
	 */
	public static ConfigUtils getConfig(MainMotd m) {		//Methods d'initialisation de la class ConfigUtils
		main = m;
		return config; // retournant
	}

	public void initDataFolder() {
		
		if(!main.getDataFolder().exists()) {
			main.getDataFolder().mkdir();
		}
		
	}
	
	public File initAndGetFile(String fileName) {
		
		File file = new File(main.getDataFolder(),fileName);
		
		if(!file.exists()) {
			try (InputStream in = main.getResourceAsStream(fileName)){
				Files.copy(in, file.toPath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return file;
	}
	
	public Configuration getConfiguration(String fileName) throws Exception {
		
		File file = new File(main.getDataFolder(),fileName);
		
		try {
			return ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
		} catch (Exception e) {
			throw new Exception( fileName + " impossible de récupérer la configuration" );
		}		
	}
	
}