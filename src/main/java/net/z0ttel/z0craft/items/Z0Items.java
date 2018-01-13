package net.z0ttel.z0craft.items;

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
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.RegistryEvent;

import net.z0ttel.z0craft.items.Z0Map;
import net.z0ttel.z0craft.blocks.Z0Blocks;
import net.z0ttel.z0craft.Z0Craft;

public class Z0Items {
	public static Item SWORD = new ItemSword(Item.ToolMaterial.DIAMOND)
		.setUnlocalizedName("sword").setRegistryName("sword");
	
	public static Item KEY = new Item()
		.setUnlocalizedName("key").setRegistryName("key");
	
	public static Item MAP = new Z0Map()
		.setUnlocalizedName("map_closed").setRegistryName("map_closed").setMaxStackSize(1);
	
	public Item items[] = new Item[]{
		SWORD, KEY, MAP
	};
	
	public Item blockitems[];
	
	public void createForBlocks(Block[] blocks) {
		Z0Craft.logger.info("creating block items");
		
		blockitems = new Item[blocks.length];
		
		int i = 0;
		for (Block block: blocks) {
			blockitems[i] = new ItemBlock(block).setRegistryName(block.getRegistryName());
			Z0Craft.logger.info("Item for block with name " + block.getRegistryName());
			//blockitems[i].setCreativeTab(Z0Craft.creativeTab);
			i++;
		}
	}
	
	@SubscribeEvent
	public void registerItems(RegistryEvent.Register<Item> event) {
		Z0Craft.logger.info("creating items for blocks");
		
		createForBlocks(Z0Craft.blocks.blocks);
		
		Z0Craft.logger.info("registering items");
		
		// Register ItemBlock Items
		for (Item itemblock : blockitems) {
			itemblock.setCreativeTab(Z0Craft.creativeTab);
			event.getRegistry().register(itemblock);
		}
		
		// Register Items
		for (Item item : items) {
			item.setCreativeTab(Z0Craft.creativeTab);
			event.getRegistry().register(item);
		}
	}
}
