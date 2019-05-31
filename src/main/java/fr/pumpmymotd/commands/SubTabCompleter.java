package fr.pumpmymotd.commands;

import java.util.List;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public interface SubTabCompleter {

	public List<String> onTabComplete(Command command, CommandSender sender, String[] args);
	
}
