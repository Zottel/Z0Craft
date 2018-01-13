package net.z0ttel.z0craft;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Level;

import net.minecraft.init.Blocks;
import net.minecraft.client.resources.I18n;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;

import net.minecraft.client.gui.GuiFlatPresets;

import net.minecraft.world.WorldType;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraft.world.gen.structure.template.Template;

import net.minecraft.launchwrapper.Launch;


import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import java.io.File;

import net.minecraftforge.event.world.WorldEvent;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import net.minecraftforge.event.RegistryEvent;

import net.z0ttel.z0craft.creativetab.Z0CreativeTab;

import net.z0ttel.z0craft.blocks.Z0Blocks;

import net.z0ttel.z0craft.items.Z0Items;

import net.z0ttel.z0craft.potion.Z0Potions;

import net.z0ttel.z0craft.world.dimension.Z0Dimension;
import net.z0ttel.z0craft.world.structures.Z0Structures;

import net.z0ttel.z0craft.math.noise.Noise2D;
import net.z0ttel.z0craft.math.noise.PerlinNoise;
import net.z0ttel.z0craft.math.noise.NoiseDistribution;

@Mod(modid = Z0Craft.MODID, name = Z0Craft.NAME, version = Z0Craft.VERSION)
public class Z0Craft
{
	public static final String MODID = "z0craft";
	public static final String NAME = "Z0Craft";
	public static final String VERSION = "1.10.2-0.1";
	
	//@Mod.Instance(Z0Craft.MODID)
	//public static Z0Craft instance;
	
	public static Z0CreativeTab	creativeTab;
	
	@SidedProxy(clientSide="net.z0ttel.z0craft.blocks.ClientZ0Blocks",
	            serverSide="net.z0ttel.z0craft.blocks.Z0Blocks")
	public static Z0Blocks blocks;
	
	@SidedProxy(clientSide="net.z0ttel.z0craft.items.ClientZ0Items",
	            serverSide="net.z0ttel.z0craft.items.Z0Items")
	public static Z0Items items;
	
	@SidedProxy(clientSide="net.z0ttel.z0craft.potion.Z0Potions",
	            serverSide="net.z0ttel.z0craft.potion.Z0Potions")
	public static Z0Potions potions;
	
	@SidedProxy(clientSide="net.z0ttel.z0craft.world.dimension.ClientZ0Dimension",
	            serverSide="net.z0ttel.z0craft.world.dimension.Z0Dimension")
	public static Z0Dimension dimension;
	
	public static final WorldType worldTypeFluff = new WorldTypeFluff();
	
	public static Z0Structures structures = new Z0Structures();
	
	public static Logger logger;
	
	public static Config config;
	
	public static boolean debugMode = false; 
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();
		
		Z0Craft.logger.info("Z0Craft/preInit called");
		Z0Craft.logger.info("source file: " + event.getSourceFile());
		if((Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment")) {
			Z0Craft.logger.info("Running in DEV ENV");
			debugMode = true;
		}
		
		config = new Config(event.getSuggestedConfigurationFile());
		
		MinecraftForge.EVENT_BUS.register(blocks);
		MinecraftForge.EVENT_BUS.register(items);
		MinecraftForge.EVENT_BUS.register(potions);
		//MinecraftForge.EVENT_BUS.register(dimension);
		
		dimension.preInit(event);
		
		this.creativeTab = new Z0CreativeTab("z0craft");
		
		//items.createForBlocks(blocks.blocks);
		
		/*
		Z0Craft.logger.info("Z0Craft/preInit testing noise");
		Noise2D noise = new PerlinNoise(0, 16);
		NoiseDistribution.Result res = NoiseDistribution.test(noise, 1000000000L);
		Z0Craft.logger.info("Z0Craft/preInit perlin: " + res);
		*/
		
		Z0Craft.logger.info("Z0Craft/preInit done");
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		Z0Craft.logger.info("Z0Craft/init called");
		
		dimension.init(event);
		
		Z0Craft.logger.info("Z0Craft/init done");
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		Z0Craft.logger.info("Z0Craft/postInit called");
		
		dimension.postInit(event);
		
		Z0Craft.logger.info("Z0Craft/postInit done");
	}
}
