package com.gmail.mikeundead;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandRollback implements CommandExecutor 
{
	private SpreadingHandler spreadingHandler;

	public CommandRollback(SpreadingHandler spreadingHandler) 
	{
		this.spreadingHandler = spreadingHandler;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) 
	{	
		if (cmd.getName().equalsIgnoreCase("rollback") && sender.isOp())
		{
			sender.sendMessage(this.spreadingHandler.Rollback());
		}
		
        if (args.length > 0) 
        {
        	sender.sendMessage(ChatColor.RED + "Too many arguments!");
        }
        
		return true;
	}
}