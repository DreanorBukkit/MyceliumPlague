package com.gmail.mikeundead;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigHandler
{
	private File configFile;
    private FileConfiguration config;
    private MyceliumPlague plugin;
    public ArrayList<Material> AllowedBlocks;
    
    public ConfigHandler(MyceliumPlague myceliumPlague)
    {
    	this.plugin = myceliumPlague;
    	this.configFile = new File(this.plugin.getDataFolder(), "config.yml");
    	
	    try 
	    {
	    	this.FirstRun();
	    } 
	    catch (Exception e) 
	    {
	        e.printStackTrace();
	    }
	    
	    this.config = new YamlConfiguration();
	    this.LoadYamls();
    }
    
	private void FirstRun() throws Exception
	{
	    if(!this.configFile.exists())
	    {
	        this.configFile.getParentFile().mkdirs();
	        
	        this.config = new YamlConfiguration();
	        
	        this.SaveYamls();
	        
	        this.Copy(this.plugin.getResource("config.yml"), configFile);
	    }
	}
	
	private void Copy(InputStream in, File file) 
	{
	    try 
	    {
	        OutputStream out = new FileOutputStream(file);
	        byte[] buf = new byte[1024];
	        int len;
	        
	        while((len=in.read(buf)) > 0)
	        {
	            out.write(buf, 0, len);
	        }
	        
	        out.close();
	        in.close();
	    } 
	    catch (Exception e) 
	    {
	        e.printStackTrace();
	    }
	}
	
	private void SaveYamls() 
	{
	    try 
	    {
	        this.config.save(configFile);
	    } 
	    catch (IOException e) 
	    {
	        e.printStackTrace();
	    }
	}
	
	private void LoadYamls() 
	{
	    try 
	    {
	        this.config.load(configFile);
	        this.LoadAllowedBlocks();
	    } 
	    catch (Exception e) 
	    {
	        e.printStackTrace();
	    }
	}
	
	private void LoadAllowedBlocks()
	{
		ArrayList<Material> blocks = new ArrayList<Material>();
		
		for(Object block : this.config.getList("AllowedBlocks"))
		{			
			blocks.add(Material.getMaterial((Integer) block));
		}
		
		this.AllowedBlocks = blocks;
	}
}
