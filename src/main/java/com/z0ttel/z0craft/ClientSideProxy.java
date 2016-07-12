package com.z0ttel.z0craft;

import org.apache.logging.log4j.Logger;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.world.WorldEvent;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.IForgeRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;



public class ClientSideProxy extends CommonProxy {
	public void preInit(FMLPreInitializationEvent event)
	{
		Z0Craft.logger.info("ClientSideProxy/preInit called");
		super.preInit(event);
		Z0Craft.logger.info("ClientSideProxy/preInit continuing");
		
		
		for (Item item: blockitems) {
			ModelResourceLocation mrl = new ModelResourceLocation(item.getRegistryName().toString(), "inventory");
			
			Z0Craft.logger.info("ClientSideProxy/preInit setting MRL for '" + item.getRegistryName() + "' to '" + mrl + "'");
			
			ModelLoader.setCustomModelResourceLocation(item, 0, mrl);
		}
		
		
		/*
		// Debug stuffs, have a look at finding items
		IForgeRegistry<Item> itemRegistry = GameRegistry.findRegistry(Item.class);
		if(itemRegistry == null) {
			Z0Craft.logger.warn("ClientSideProxy/preInit GameRegistry for Items NOT FOUND");
			return;
		}
		for(Item item: itemRegistry.getValues()) {
			Z0Craft.logger.warn("ClientSideProxy/preInit Item: " + item + " with registry name '" + item.getRegistryName() + "'");
		}
		*/
	}
	
	public void init(FMLInitializationEvent event)
	{
		Z0Craft.logger.info("ClientSideProxy/init called");
		super.init(event);
		Z0Craft.logger.info("ClientSideProxy/init continuing");
	}
	
	public void postInit(FMLPostInitializationEvent event)
	{
		Z0Craft.logger.info("ClientSideProxy/postInit called");
		super.postInit(event);
		Z0Craft.logger.info("ClientSideProxy/postInit continuing");
	}
}

