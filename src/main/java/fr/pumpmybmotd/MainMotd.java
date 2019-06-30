package fr.pumpmybmotd;

import fr.pumpmybmotd.commands.AddMotdSubCommand;
import fr.pumpmybmotd.commands.HelpMotdSubCommand;
import fr.pumpmybmotd.commands.MotdCommandExecutor;
import fr.pumpmybmotd.commands.ReloadMotdSubCommand;
import fr.pumpmybmotd.config.ConfigUtils;
import fr.pumpmybmotd.motd.PingManager;
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
		motdCommandExec.addSubCommand("reload", "pumpmymotd.command.reload" , new ReloadMotdSubCommand(this.manager));
		motdCommandExec.addSubCommand("add", "pumpmymotd.command.add" , new AddMotdSubCommand(this.manager));
		
		
		getProxy().getPluginManager().registerCommand(this, motdCommandExec);
		
	}
	
}
