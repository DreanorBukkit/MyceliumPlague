package com.gmail.mikeundead;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockSpreadEvent;

public class BlockListener implements Listener 
{
	private ConfigHandler config;
	private SpreadingHandler spread;
	
	public BlockListener(ConfigHandler configHandler, SpreadingHandler spreadingHandler) 
	{
		this.config = configHandler;
		this.spread = spreadingHandler;
	}

	@EventHandler
    public void onSpread(BlockSpreadEvent event) 
	{
		if(event.getSource().getType() == Material.MYCEL)
		{
			this.SetGrowPattern(event.getBlock().getLocation());
		}		
		
		if(event.getSource().getType() == Material.GRASS)
		{
			if(event.getBlock().getBiome() == Biome.MUSHROOM_ISLAND)
			{
				this.SetGrowPattern(event.getBlock().getLocation());
				event.setCancelled(true);
			}
		}
		
		if(this.spread.spreadLocations.size()>100)
		{
			this.spread.SaveLocations();
		}
    }
	
	@EventHandler
	public void onBlockPlace (BlockPlaceEvent event) 
	{
		Block placedBlock = event.getBlockPlaced();

		if(placedBlock.getType() == Material.MYCEL)
		{
			Location location = placedBlock.getLocation();
			
			HashMap<Material, Biome> map = new HashMap<Material, Biome>();
			map.put(event.getBlockReplacedState().getType(), event.getBlockReplacedState().getBlock().getBiome());
			this.spread.spreadLocations.put(location, map);
			
			this.SetBlocksOnPlace(location);
		}
	}	
	
	private void SetGrowPattern(Location loc)
	{
		int xa = loc.getBlockX();
    	int za = loc.getBlockZ();
    	int ya = loc.getBlockY();
    	
    	for (int y = ya-1; y < loc.getBlockY()+2; y++)
    	{
	    	for (int x = xa-1; x < loc.getBlockX()+2; x++) 
			{
				for (int z = za-1; z < loc.getBlockZ()+2; z++) 
			    {
					if(z == za-1 && x == xa -1 || z == za-1 && x == xa +1 || z == za+1 && x == xa -1 || z == za+1 && x == xa +1)
					{
						Location location = new Location(loc.getWorld(), x, y, z);
						
						if(this.BlockIsListed(location.getBlock().getType()))
						{					
							HashMap<Material, Biome> map = new HashMap<Material, Biome>();
							map.put(location.getBlock().getType(), Biome.PLAINS);
							this.spread.spreadLocations.put(location, map);
							
							location.getBlock().setType(Material.DIRT);
							location.getBlock().setBiome(Biome.MUSHROOM_ISLAND);
						}
					}
					else
					{
						Location location = new Location(loc.getWorld(), x, y, z);
						
						if(this.BlockIsListed(location.getBlock().getType()))
						{			
							HashMap<Material, Biome> map = new HashMap<Material, Biome>();
							map.put(location.getBlock().getType(), Biome.PLAINS);
							this.spread.spreadLocations.put(location, map);
							
							location.getBlock().setType(Material.MYCEL);
							location.getBlock().setBiome(Biome.MUSHROOM_ISLAND);
						}
					}
			    }
			}
    	}
	}
	
	private boolean BlockIsListed(Material material) 
	{
		for(Material block : this.config.AllowedBlocks)
		{
			if(material == block)
			{
				return true;
			}
		}
		
		return false;
	}

	private void SetBlocksOnPlace(Location loc)
	{
		int xa = loc.getBlockX();
    	int za = loc.getBlockZ();
    	int ya = loc.getBlockY();
    	
    	for (int y = ya-2; y < loc.getBlockY()+3; y++)
    	{
			for (int x = xa-2; x < loc.getBlockX()+3; x++) 
			{
				for (int z = za-2; z < loc.getBlockZ()+3; z++) 
			    {
					if(z == za-2 || x == xa-2)
					{
						Location location = new Location(loc.getWorld(), x, y, z);
						
						if(this.BlockIsListed(location.getBlock().getType()))
						{
							HashMap<Material, Biome> map = new HashMap<Material, Biome>();
							map.put(location.getBlock().getType(), Biome.PLAINS);
							this.spread.spreadLocations.put(location, map);
							
							location.getBlock().setType(Material.DIRT);
							location.getBlock().setBiome(Biome.MUSHROOM_ISLAND);
						}
					}
					else
					{
						if(x == xa+2 || z == za+2)
						{
							Location location = new Location(loc.getWorld(), x, y, z);
							
							if(this.BlockIsListed(location.getBlock().getType()))
							{
								HashMap<Material, Biome> map = new HashMap<Material, Biome>();
								map.put(location.getBlock().getType(), Biome.PLAINS);
								this.spread.spreadLocations.put(location, map);
								
								location.getBlock().setType(Material.DIRT);
								location.getBlock().setBiome(Biome.MUSHROOM_ISLAND);
							}
						}
						else
						{
							Location location = new Location(loc.getWorld(), x, y, z);
							
							if(this.BlockIsListed(location.getBlock().getType()))
							{
								HashMap<Material, Biome> map = new HashMap<Material, Biome>();
								map.put(location.getBlock().getType(), Biome.PLAINS);
								this.spread.spreadLocations.put(location, map);
								
								location.getBlock().setType(Material.MYCEL);
								location.getBlock().setBiome(Biome.MUSHROOM_ISLAND);
							}
						}
					}
			    }
			}
    	}
	}
}
