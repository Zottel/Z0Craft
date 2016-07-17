package com.z0ttel.z0craft.items;

import java.util.Map;
import java.util.HashMap;

import org.apache.logging.log4j.Logger;

import net.minecraft.client.resources.I18n;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSword;
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

import com.z0ttel.z0craft.blocks.Z0Blocks;
import com.z0ttel.z0craft.Z0Craft;

public class Z0Items {
	public static Item SWORD = new ItemSword(Item.ToolMaterial.DIAMOND)
		.setUnlocalizedName("sword").setRegistryName("sword");
	
	public static Item KEY = new Item()
		.setUnlocalizedName("key").setRegistryName("key");
	
	public Item items[] = new Item[]{
		SWORD, KEY
	};
	
	public Item blockitems[];
	
	public void createForBlocks(Block[] blocks) {
		Z0Craft.logger.info("creating block items");
		
		blockitems = new Item[blocks.length];
		
		int i = 0;
		for (Block block: blocks) {
			blockitems[i] = new ItemBlock(block).setRegistryName(block.getRegistryName());
			//blockitems[i].setCreativeTab(Z0Craft.creativeTab);
			i++;
		}
	}
	
	public void registerItem(Item item) {
		//Z0Craft.logger.info("registering item'" + itemblock.getUnlocalizedName() + "' with registry name '" + itemblock.getRegistryName() + "'");
		item.setCreativeTab(Z0Craft.creativeTab);
		GameRegistry.register(item);
	}
	
	public void preInit(FMLPreInitializationEvent event) {
		Z0Craft.logger.info("registering items");
		
		// Register ItemBlock Items
		for (Item itemblock : blockitems) {
			registerItem(itemblock);
		}
		
		// Register Items
		for (Item item : items) {
			registerItem(item);
		}
	}
	
	public void init(FMLInitializationEvent event) {
	}
	
	public void postInit(FMLPostInitializationEvent event) {
	}
}
