package fr.pumpmybmotd.commands;

import java.util.List;

import fr.pumpmybmotd.MainMotd;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.plugin.Command;

public class HelpMotdSubCommand implements ISubCommand{

	private MainMotd main;
	
	public HelpMotdSubCommand(MainMotd main) {
		this.main = main;
	}

	@Override
	public void onSubCommand(Command exec, CommandSender sender, List<String> args) {

		sender.sendMessage(new ComponentBuilder("PumpMyMotd[" + this.main.getDescription().getVersion() + "] commmand : \"pumpmymotd [help/reload/add/list/get]\"").color(ChatColor.AQUA).create());

	}

	public void onSubCommand(Command motdCommandExecutor, CommandSender sender) {

		this.onSubCommand(motdCommandExecutor, sender, null);

	}


}
