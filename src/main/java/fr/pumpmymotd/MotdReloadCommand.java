package fr.pumpmymotd;

import fr.pumpmymotd.motd.PingManager;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.plugin.Command;

public class MotdReloadCommand extends Command {

	private PingManager manager;
	
	private MotdReloadCommand(String name) {
		super(name);
	}

	public MotdReloadCommand(PingManager manager) {
		
		this("pmmotd-reload");
		this.manager = manager;
		
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		
		try {
			manager.load();
			sender.sendMessage(new ComponentBuilder("Motd reloaded succesfuly !").color(ChatColor.AQUA).create());
		} catch (Exception e) {
			
			e.printStackTrace();
			sender.sendMessage(new ComponentBuilder("Motd reloaded ERROR !").color(ChatColor.RED).create());
			
		}

	}

}
