package fr.pumpmybmotd;

import java.util.Map.Entry;

import fr.pumpmybmotd.commands.AddMotdSubCommand;
import fr.pumpmybmotd.commands.GetMotdSubCommand;
import fr.pumpmybmotd.commands.HelpMotdSubCommand;
import fr.pumpmybmotd.commands.ListMotdSubCommand;
import fr.pumpmybmotd.commands.MotdCommandExecutor;
import fr.pumpmybmotd.commands.ReloadMotdSubCommand;
import fr.pumpmybmotd.config.ConfigUtils;
import fr.pumpmybmotd.motd.PingManager;
import net.md_5.bungee.api.config.ListenerInfo;
import net.md_5.bungee.api.config.ServerInfo;
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
		motdCommandExec.addSubCommand("list", "pumpmymotd.command.list", new ListMotdSubCommand(this.manager));
		motdCommandExec.addSubCommand("get", "pumpmymotd.command.get", new GetMotdSubCommand(this.manager));
		
		
		getProxy().getPluginManager().registerCommand(this, motdCommandExec);
		
	}
	
	@SuppressWarnings("deprecation")
	public ServerInfo getServerInfoWithForcedHost(String host) {
		String serverName = new String();
		for (ListenerInfo listener : getProxy().getConfig().getListeners()) {

			for (Entry<String, String> entry : listener.getForcedHosts().entrySet()) {
				if(entry.getKey().equals(host)) {
					serverName = entry.getValue();
					break;
				}
			}
			if(!serverName.isEmpty()) {
				break;
			}
		}		
		
		if(this.getProxy().getServers().keySet().contains(serverName)) {
			return this.getProxy().getServerInfo(serverName);
		}
		return null;		
	}
	
}
