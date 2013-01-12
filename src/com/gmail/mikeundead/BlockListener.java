package com.gmail.mikeundead;

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
    }
	
	@EventHandler
	public void onBlockPlace (BlockPlaceEvent event) 
	{
		Block placedBlock = event.getBlockPlaced();

		if(placedBlock.getType() == Material.MYCEL)
		{
			Location loc = placedBlock.getLocation();
			
			this.SetBlocks(loc);
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
						Location l = new Location(loc.getWorld(), x, y, z);
						
						if(l.getBlock().getType() == Material.GRASS || l.getBlock().getType() == Material.DIRT)
						{					
							l.getBlock().setType(Material.DIRT);
							l.getBlock().setBiome(Biome.MUSHROOM_ISLAND);
						}
					}
					else
					{
						Location l = new Location(loc.getWorld(), x, y, z);
						
						if(l.getBlock().getType() == Material.GRASS || l.getBlock().getType() == Material.DIRT)
						{					
							l.getBlock().setType(Material.MYCEL);
							l.getBlock().setBiome(Biome.MUSHROOM_ISLAND);
						}
					}
			    }
			}
    	}
	}
	
	private void SetBlocks(Location loc)
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
						Location l = new Location(loc.getWorld(), x, y, z);
						
						if(l.getBlock().getType() == Material.GRASS || l.getBlock().getType() == Material.DIRT)
						{
							l.getBlock().setType(Material.DIRT);
							l.getBlock().setBiome(Biome.MUSHROOM_ISLAND);
						}
					}
					else
					{
						if(x == xa+2 || z == za+2)
						{
							Location l = new Location(loc.getWorld(), x, loc.getBlockY(), z);
							
							if(l.getBlock().getType() == Material.GRASS)
							{
								l.getBlock().setType(Material.DIRT);
								l.getBlock().setBiome(Biome.MUSHROOM_ISLAND);
							}
						}
						else
						{
							Location l = new Location(loc.getWorld(), x, y, z);
							
							if(l.getBlock().getType() == Material.GRASS || l.getBlock().getType() == Material.DIRT)
							{
								l.getBlock().setType(Material.MYCEL);
								l.getBlock().setBiome(Biome.MUSHROOM_ISLAND);
							}
						}
					}
			    }
			}
    	}
	}
}
