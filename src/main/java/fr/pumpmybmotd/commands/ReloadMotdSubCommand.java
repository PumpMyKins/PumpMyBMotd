package fr.pumpmybmotd.commands;

import java.util.List;

import fr.pumpmybmotd.motd.PingManager;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.plugin.Command;

public class ReloadMotdSubCommand implements ISubCommand{
	
	private PingManager manager;
	
	public ReloadMotdSubCommand(PingManager m) {
		this.manager = m;
	}
	
	@Override
	public void onSubCommand(Command exec, CommandSender sender, List<String> args) {

		try {
			manager.clearPings();
			manager.load();
			sender.sendMessage(new ComponentBuilder("PumpMyMotd configuration reloaded succesfuly !").color(ChatColor.AQUA).create());
		} catch (Exception e) {
			
			e.printStackTrace();
			sender.sendMessage(new ComponentBuilder("PumpMyMotd configuration reloaded ERROR !").color(ChatColor.RED).create());
			
		}
		
	}	

}
