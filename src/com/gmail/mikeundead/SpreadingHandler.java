package com.gmail.mikeundead;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class SpreadingHandler 
{
	private File configFile;
    private FileConfiguration config;
    private MyceliumPlague plugin;
    public HashMap<Location, Map<Material, Biome>> spreadLocations;
	private int count;
    
	public SpreadingHandler(MyceliumPlague myceliumPlague) 
    {
    	this.plugin = myceliumPlague;
    	this.configFile = new File(this.plugin.getDataFolder(), "spread.yml");
    	this.spreadLocations = new HashMap<Location, Map<Material, Biome>>();
    	
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
	        
	        this.Copy(this.plugin.getResource("spread.yml"), configFile);
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
	    } 
	    catch (Exception e) 
	    {
	        e.printStackTrace();
	    }
	}

	public void SaveLocations()
	{
		for(Entry<Location, Map<Material, Biome>> spreadLocation : this.spreadLocations.entrySet())
		{
			Location loc = spreadLocation.getKey();
			Material mat = null;
			Biome bio = null;
			String key = Integer.toString(this.count);
			
			for(Material material : spreadLocation.getValue().keySet())
			{
				mat = material;
			}
			
			for(Biome biome : spreadLocation.getValue().values())
			{
				bio = biome;
			}
			
			this.config.set(key+".World", loc.getWorld().getName());
			this.config.set(key+".Location.X", loc.getX());
			this.config.set(key+".Location.Y", loc.getY());
			this.config.set(key+".Location.Z", loc.getZ());
			this.config.set(key+".Material", mat.getId());
			this.config.set(key+".Biome", bio.toString());
			
			this.count++;
		}
		
		this.spreadLocations.clear();
		
		this.SaveYamls();
	}
	
	public String Rollback()
	{
		this.SaveLocations();
		this.LoadYamls();
		
		int countReset = 0;
		
		for(int i=0; i < 1000000000; i++)
		{
			if(this.config.getString(i+".World") != null)
			{
				String world = this.config.getString(i+".World");
				int x = this.config.getInt(i+".Location.X");
				int y = this.config.getInt(i+".Location.Y");
				int z = this.config.getInt(i+".Location.Z");
				Material mat = Material.getMaterial(this.config.getInt(i+".Material"));
				Biome bio = Biome.valueOf(this.config.getString(i+".Biome"));
				
				Location loc = new Location(this.plugin.getServer().getWorld(world), x, y, z);
				loc.getBlock().setType(mat);
				loc.getBlock().setBiome(bio);
				
				this.config.set(i+".World", null);
				this.config.set(i+".Location.X", null);
				this.config.set(i+".Location.Y", null);
				this.config.set(i+".Location.Z", null);
				this.config.set(i+".Material", null);
				this.config.set(i+".Biome", null);
				this.config.set(i+"", null);
				
				countReset++;
			}
			else
			{
				this.count = 0;
				return String.format("Rolled back %s Locations.", countReset);
			}
		}
		return null;
	}
}
