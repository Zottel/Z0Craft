package com.z0ttel.z0craft.blocks;

import java.util.Map;
import java.util.HashMap;

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

import com.z0ttel.z0craft.Z0Craft;

public class Z0Blocks {
	public static Block PORTAL = new BlockZ0Portal()
		.setUnlocalizedName("portal").setRegistryName("portal")
		.setBlockUnbreakable().setLightLevel(0.45F);

	public static Block UNBREAKABLE = new Block(Material.ROCK)
		.setUnlocalizedName("unbreakable").setRegistryName("unbreakable")
		.setBlockUnbreakable();

	public static Block ROCK = new Block(Material.ROCK)
		.setUnlocalizedName("rock").setRegistryName("rock")
		.setHardness(3.0F);

	public static Block GLASS = new BlockZ0Alpha(Material.GLASS)
		.setUnlocalizedName("glass").setRegistryName("glass")
		.setHardness(3.0F);

	public static Block LIGHT = new Block(Material.GLASS)
		.setUnlocalizedName("light").setRegistryName("light")
		.setHardness(3.0F).setLightLevel(0.6F);
	
	public static Block BRICK = new Block(Material.ROCK)
		.setUnlocalizedName("brick").setRegistryName("brick")
		.setHardness(3.0F);
	
	public static Block blocks[] = new Block[]{
		PORTAL, UNBREAKABLE, ROCK, GLASS, LIGHT, BRICK
	};
	
	public void preInit(FMLPreInitializationEvent event) {
		Z0Craft.logger.info("registering blocks");
		// Register Blocks
		for (Block block: blocks) {
			//Z0Craft.logger.info("registering block '" + block.getUnlocalizedName() + "' with registry name '" + block.getRegistryName() + "'");
			GameRegistry.register(block);
		}
	}
	
	public void init(FMLInitializationEvent event) {
	}
	
	public void postInit(FMLPostInitializationEvent event) {
	}
	
}
