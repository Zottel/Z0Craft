package com.z0ttel.z0craft;

import org.apache.logging.log4j.Logger;

import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import net.minecraftforge.fml.common.registry.GameRegistry;

import com.z0ttel.z0craft.blocks.BlockZ0Portal;

public class CommonProxy {
	public Block blocks[] = new Block[]{
		new Block(Material.ROCK)
		         .setHardness(3.0F)
		         .setRegistryName("lerock")
		         .setUnlocalizedName("lerock"),
		new Block(Material.ROCK)
		         .setHardness(3.0F)
		         .setUnlocalizedName("lelight")
		         .setRegistryName("lelight")
		         .setLightLevel(1.0F),
		new BlockZ0Portal()
		         .setUnlocalizedName("leportal")
		         .setRegistryName("leportal")
	};
	public Item blockitems[];
	
	public void preInit(FMLPreInitializationEvent event)
	{
		Z0Craft.logger.info("CommonProxy/preInit called");
		
		
		// Register Blocks
		for (Block block: blocks) {
			Z0Craft.logger.info("CommonProxy/preInit: registering block '" + block.getUnlocalizedName() + "' with registry name '" + block.getRegistryName() + "'");
			GameRegistry.register(block);
		}
		
		
		// Build ItemBlock Items for the blocks
		Z0Craft.logger.info("CommonProxy/preInit creating block items");
		blockitems = new Item[blocks.length];
		int i = 0;
		for (Block block: blocks) {
			blockitems[i] = new ItemBlock(block).setRegistryName(block.getRegistryName());
			i++;
		}
		
		// Register ItemBlock Items
		for (Item itemblock: blockitems) {
			Z0Craft.logger.info("CommonProxy/preInit: registering itemblock '" + itemblock.getUnlocalizedName() + "' with registry name '" + itemblock.getRegistryName() + "'");
			GameRegistry.register(itemblock);
		}
	}
	
	public void init(FMLInitializationEvent event)
	{
		Z0Craft.logger.info("CommonProxy/init called");
	}
	
	public void postInit(FMLPostInitializationEvent event)
	{
		Z0Craft.logger.info("CommonProxy/postInit called");
	}
	
}
