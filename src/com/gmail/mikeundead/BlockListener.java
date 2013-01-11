package com.gmail.mikeundead;

import java.util.ArrayList;

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
			
			this.SetBlocks(event.getBlock().getLocation());
		}		
		
		if(event.getSource().getType() == Material.GRASS)
		{
			if(event.getBlock().getBiome() == Biome.MUSHROOM_ISLAND)
			{
				Location l = event.getBlock().getLocation();
				
				this.SetBlocks(event.getBlock().getLocation());
				
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
	
	private void SetBlocks(Location loc)
	{
		int xa = loc.getBlockX();
    	int za = loc.getBlockZ();
    	
		for (int x = xa; x < loc.getBlockX()+5; x++) 
		{
			for (int z = za; z < loc.getBlockZ()+5; z++) 
		    {
				if(z == za || x == xa)
				{
					Location l = new Location(loc.getWorld(), x, loc.getBlockY(), z);
					
					if(l.getBlock().getType() != Material.AIR && l.getBlock().getType() != Material.MYCEL)
					{
						l.getBlock().setType(Material.DIRT);
						l.getBlock().setBiome(Biome.MUSHROOM_ISLAND);
					}
				}
				else
				{
					if(x == xa+4 || z == za+4)
					{
						Location l = new Location(loc.getWorld(), x, loc.getBlockY(), z);
						
						if(l.getBlock().getType() != Material.AIR && l.getBlock().getType() != Material.MYCEL)
						{
							l.getBlock().setType(Material.DIRT);
							l.getBlock().setBiome(Biome.MUSHROOM_ISLAND);
						}
					}
					else
					{
						Location l = new Location(loc.getWorld(), x, loc.getBlockY(), z);
						if(l.getBlock().getType() != Material.AIR && l.getBlock().getType() != Material.MYCEL)
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
