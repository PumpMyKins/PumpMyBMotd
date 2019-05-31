package fr.pumpmymotd.commands;

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
		
		if(args.size() == 2) {
			System.out.println(args.get(1));
			System.out.println(args.get(2));
			if(!this.onTabComplete(exec, sender, (String[]) args.toArray()).contains(args.get(1))) {
				
				
				
			}
			
		}else{
			sender.sendMessage(new TextComponent("§cInvalid syntax !"));
			
		}
		
	}

	@Override
	public List<String> onTabComplete(Command command, CommandSender sender, String[] args) {
		
		List<String> list = new ArrayList<String>();
		
		for (String string : this.manager.getForcedHost()) {
			
			if(!this.manager.getPings().containsKey(string)) {
				
				list.add(string);
				
			}
			
		}
		
		return list;
		
	}	

}
