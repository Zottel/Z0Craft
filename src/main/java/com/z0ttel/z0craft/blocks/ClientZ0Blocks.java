package com.z0ttel.z0craft.blocks;

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

public class ClientZ0Blocks extends Z0Blocks {
	public void preInit(FMLPreInitializationEvent event)
	{
		super.preInit(event);
		
		for (Item item: blockitems) {
			ModelResourceLocation mrl = new ModelResourceLocation(item.getRegistryName().toString(), "inventory");
			
			ModelLoader.setCustomModelResourceLocation(item, 0, mrl);
		}
		
		/*
		// Debug stuffs, have a look at finding items
		IForgeRegistry<Item> itemRegistry = GameRegistry.findRegistry(Item.class);
		if(itemRegistry == null) {
			return;
		}
		for(Item item: itemRegistry.getValues()) {
		}
		*/
	}
	
	public void init(FMLInitializationEvent event)
	{
		super.init(event);
	}
	
	public void postInit(FMLPostInitializationEvent event)
	{
		super.postInit(event);
	}
}


