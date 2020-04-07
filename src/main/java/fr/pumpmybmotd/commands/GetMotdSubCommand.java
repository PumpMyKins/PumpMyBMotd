package fr.pumpmybmotd.commands;

import java.util.ArrayList;
import java.util.List;

import fr.pumpmybmotd.motd.Ping;
import fr.pumpmybmotd.motd.PingManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class GetMotdSubCommand implements ISubCommand,ISubTabCompleter{

	private PingManager manager;

	public GetMotdSubCommand(PingManager m) {
		this.manager = m;
	}

	@Override
	public void onSubCommand(Command exec, CommandSender sender, List<String> args) {

		if(args.size() == 1) {

			if(this.onTabComplete(exec, sender, args).contains(args.get(0)) && !this.manager.getPings().containsKey(args.get(0))) {

				String forcedHost = args.get(0);
				Ping ping;
				if(forcedHost.equals("default")) {
					ping = this.manager.getDefaultPing();
				}else {
					ping = this.manager.getPing(forcedHost);
				}
				
				sender.sendMessage(new TextComponent("§bConfiguration : " + forcedHost));
				sender.sendMessage(new TextComponent("§bLine1 : §r" + ping.getLine1()));
				sender.sendMessage(new TextComponent("§bLine2 : §r" + ping.getLine2()));
				sender.sendMessage(new TextComponent("§bFavicon name : " + ping.getFaviconName()));
				sender.sendMessage(new TextComponent("§bFml support : " + ping.hasFmlSupport()));
				sender.sendMessage(new TextComponent("§bMax players : " + ping.getMaxPlayers()));
				
				

			}else {

				sender.sendMessage(new TextComponent("§cUnknow Forced Host !"));

			}

		}else{
			sender.sendMessage(new TextComponent("§cInvalid syntax !"));			
		}

	}

	@Override
	public List<String> onTabComplete(Command command, CommandSender sender, List<String> args) {

		List<String> list = new ArrayList<String>();

		for (String string : this.manager.getForcedHost()) {

			if(!this.manager.getPings().containsKey(string)) {

				list.add(string);

			}

		}

		list.add("default");

		return list;

	}	

}
