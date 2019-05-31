package fr.pumpmymotd.commands;

import java.util.List;

import fr.pumpmymotd.motd.PingManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class AddMotdSubCommand implements ISubCommand{
	
	private PingManager manager;
	
	public AddMotdSubCommand(PingManager m) {
		this.manager = m;
	}
	
	@Override
	public void onSubCommand(Command exec, CommandSender sender, List<String> args) {

		
		
	}	

}
