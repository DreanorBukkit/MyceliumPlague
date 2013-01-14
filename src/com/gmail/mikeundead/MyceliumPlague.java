package com.gmail.mikeundead;

import org.bukkit.plugin.java.JavaPlugin;

public class MyceliumPlague extends JavaPlugin
{
	private BlockListener blockListener;
    private ConfigHandler configHandler;
    private SpreadingHandler spreadingHandler;
	private CommandRollback commandRollback;
    
	public void onEnable()
	{
		this.spreadingHandler = new SpreadingHandler(this);
		this.configHandler = new ConfigHandler(this);
		this.blockListener = new BlockListener(this.configHandler, this.spreadingHandler);

		this.getServer().getPluginManager().registerEvents(this.blockListener, this);
		this.commandRollback = new CommandRollback(this.spreadingHandler);
		
		this.getCommand("rollback").setExecutor(this.commandRollback);
		
		this.getLogger().info("MyceliumPlague has been enabled.");
	}
	
	public void onDisable()
	{ 
		this.spreadingHandler.SaveLocations();
		this.getLogger().info("MyceliumPlague has been disabled.");
	}
}