package com.z0ttel.z0craft.items;

import org.apache.logging.log4j.Logger;

import net.minecraft.item.Item;

import net.minecraftforge.common.MinecraftForge;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

import com.z0ttel.z0craft.Z0Craft;

public class ClientZ0Items extends Z0Items {
	private void registerItemModel(Item item) {
		ModelResourceLocation mrl = new ModelResourceLocation(item.getRegistryName().toString(), "inventory");
		
		ModelLoader.setCustomModelResourceLocation(item, 0, mrl);
	}
	
	public void preInit(FMLPreInitializationEvent event)
	{
		super.preInit(event);
		
		Z0Craft.logger.info("registering resource locations for item models");
		
		for (Item item: blockitems) {
			registerItemModel(item);
		}
		
		for (Item item: items) {
			registerItemModel(item);
		}
	}
}
