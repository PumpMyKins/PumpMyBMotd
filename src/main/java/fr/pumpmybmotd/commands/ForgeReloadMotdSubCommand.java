package fr.pumpmybmotd.commands;

import java.util.List;

import fr.pumpmybmotd.motd.PingManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class ForgeReloadMotdSubCommand implements ISubCommand{

	private PingManager manager;

	public ForgeReloadMotdSubCommand(PingManager m) {
		this.manager = m;
	}

	@Override
	public void onSubCommand(Command exec, CommandSender sender, List<String> args) {

		if(args.size() == 0) {

			sender.sendMessage(new TextComponent("§bFml Support : refreshing all host !"));
			this.manager.getForgePingSupport().refreshAll();


		}else{
			sender.sendMessage(new TextComponent("§cInvalid syntax !"));			
		}

	}

	public void onSubCommand(Command motdCommandExecutor, CommandSender sender) {

		this.onSubCommand(motdCommandExecutor, sender, null);

	}


}
