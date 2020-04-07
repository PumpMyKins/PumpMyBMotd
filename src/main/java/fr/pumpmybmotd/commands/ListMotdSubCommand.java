package fr.pumpmybmotd.commands;

import java.util.ArrayList;
import java.util.List;

import fr.pumpmybmotd.motd.PingManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class ListMotdSubCommand implements ISubCommand,ISubTabCompleter{

	private PingManager manager;

	public ListMotdSubCommand(PingManager m) {
		this.manager = m;
	}

	@Override
	public void onSubCommand(Command exec, CommandSender sender, List<String> args) {

		if(args.size() != 0) {

			sender.sendMessage(new TextComponent("§bConfigured forced-host motd :"));
			sender.sendMessage(new TextComponent("§b\t - default"));
			for (String string : this.manager.getPings().keySet()) {
				sender.sendMessage(new TextComponent("§b\t - " + string));
			}


		}else{
			sender.sendMessage(new TextComponent("§cInvalid syntax !"));			
		}

	}

	@Override
	public List<String> onTabComplete(Command command, CommandSender sender, List<String> args) {

		List<String> list = new ArrayList<String>();		
		return list;

	}	

}
