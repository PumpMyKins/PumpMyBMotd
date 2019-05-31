package fr.pumpmymotd;

import fr.pumpmymotd.commands.HelpMotdSubCommand;
import fr.pumpmymotd.commands.MotdCommandExecutor;
import fr.pumpmymotd.commands.ReloadMotdSubCommand;
import fr.pumpmymotd.config.ConfigUtils;
import fr.pumpmymotd.motd.PingManager;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;

public class MainMotd extends Plugin{

	private PingManager manager;
	
	@Override
	public void onEnable() {
		
		ConfigUtils config = new ConfigUtils(this);
		config.initDataFolder();
		
		this.manager = new PingManager(config);
		
		try {
			
			manager.load();
			
		} catch (Exception e) {

			e.printStackTrace();
			this.getProxy().stop();
			return;
			
		}
		
		getProxy().getPluginManager().registerListener(this, new ProxyPingListener(manager));
		
		MotdCommandExecutor motdCommandExec = new MotdCommandExecutor(this);
		
		motdCommandExec.addSubCommand("help", new HelpMotdSubCommand());
		motdCommandExec.addSubCommand("reload", new ReloadMotdSubCommand(this.manager));
		
		
		getProxy().getPluginManager().registerCommand(this, motdCommandExec);
		
	}
	
}
