package com.gmail.mikeundead;

import org.bukkit.plugin.java.JavaPlugin;

public class MyceliumPlague extends JavaPlugin
{
	private BlockListener blockListener;
    private ConfigHandler configHandler;
    
	public void onEnable()
	{			
		this.configHandler = new ConfigHandler(this);
		this.blockListener = new BlockListener(this.configHandler);

		this.getServer().getPluginManager().registerEvents(this.blockListener, this);
	    
		this.getLogger().info("MyceliumPlague has been enabled.");
	}
	
	public void onDisable()
	{ 
		this.getLogger().info("MyceliumPlague has been disabled.");
	}
}