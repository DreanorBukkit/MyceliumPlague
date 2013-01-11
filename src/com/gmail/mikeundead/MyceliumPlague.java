package com.gmail.mikeundead;

import org.bukkit.plugin.java.JavaPlugin;

public class MyceliumPlague extends JavaPlugin
{
	private BlockListener blockListener;

	public void onEnable()
	{			
		this.blockListener = new BlockListener();
		
		this.getLogger().info("MyceliumPlague has been enabled.");
		
		this.getServer().getPluginManager().registerEvents(this.blockListener, this);
	}
	 
	public void onDisable()
	{ 
		this.getLogger().info("MyceliumPlague has been disabled.");
	}
}
