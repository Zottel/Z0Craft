package com.z0ttel.z0craft.blocks;

import java.util.Map;
import java.util.HashMap;

import org.apache.logging.log4j.Logger;

import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
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
		.setBlockUnbreakable().setResistance(6000000.0F)
		.setLightLevel(0.45F);

	public static Block UNBREAKABLE = new Block(Material.ROCK)
		.setUnlocalizedName("unbreakable").setRegistryName("unbreakable")
		.setBlockUnbreakable().setResistance(6000000.0F);
	
	public static Block UNBREAKABLE_STAIRS = new Z0Stairs(UNBREAKABLE.getDefaultState())
		.setUnlocalizedName("unbreakable_stairs").setRegistryName("unbreakable_stairs");

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
	
	//(new BlockStairs(block.getDefaultState())).setUnlocalizedName("stairsStone"));
	public static Block STAIRS = new Z0Stairs(BRICK.getDefaultState())
		.setUnlocalizedName("stairs").setRegistryName("stairs")
		.setHardness(3.0F);
	
	public static Block DUST = new BlockDust()
		.setUnlocalizedName("dust").setRegistryName("dust")
		.setHardness(5.0F); // really hard to get rid of XD

	public static Block blocks[] = new Block[]{
		PORTAL, UNBREAKABLE, UNBREAKABLE_STAIRS, ROCK, GLASS, LIGHT, BRICK, STAIRS, DUST
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
