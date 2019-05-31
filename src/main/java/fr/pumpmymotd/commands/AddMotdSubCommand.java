package fr.pumpmymotd.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import fr.pumpmymotd.motd.PingManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class AddMotdSubCommand implements ISubCommand,ISubTabCompleter{
	
	private PingManager manager;
	
	public AddMotdSubCommand(PingManager m) {
		this.manager = m;
	}
	
	@Override
	public void onSubCommand(Command exec, CommandSender sender, List<String> args) {

		System.out.println(args.size());
		
		if(args.size() == 1) {
			System.out.println(args.get(0));
			if(this.onTabComplete(exec, sender, args).contains(args.get(0))) {
				
				try {
					this.manager.initMotdFileConfiguration(new File(this.manager.getConfig().getDataFolder(),args.get(0)));
					sender.sendMessage(new TextComponent("§bConfiguration file successfully created !"));
				} catch (Exception e) {
					e.printStackTrace();
					sender.sendMessage(new TextComponent("§cError on file creation !"));
				}
				
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
		
		return list;
		
	}	

}
