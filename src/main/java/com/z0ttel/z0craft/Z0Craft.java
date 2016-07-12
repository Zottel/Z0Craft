package com.z0ttel.z0craft;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Level;

import net.minecraft.init.Blocks;
import net.minecraft.client.resources.I18n;

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

import com.z0ttel.z0craft.creativetab.Z0CreativeTab;

import com.z0ttel.z0craft.blocks.Z0Blocks;
import com.z0ttel.z0craft.blocks.ClientZ0Blocks;

import com.z0ttel.z0craft.dimension.Z0Dimension;
import com.z0ttel.z0craft.dimension.ClientZ0Dimension;

@Mod(modid = Z0Craft.MODID, version = Z0Craft.VERSION)
public class Z0Craft
{
	public static final String MODID = "z0craft";
	public static final String VERSION = "0.1";
	
	@Mod.Instance(Z0Craft.MODID)
	public static Z0Craft instance;
	
	public static Z0CreativeTab	creativeTab;
	
	@SidedProxy(clientSide="com.z0ttel.z0craft.blocks.ClientZ0Blocks",
	            serverSide="com.z0ttel.z0craft.blocks.Z0Blocks")
	public static Z0Blocks blocks;
	
	@SidedProxy(clientSide="com.z0ttel.z0craft.dimension.ClientZ0Dimension",
	            serverSide="com.z0ttel.z0craft.dimension.Z0Dimension")
	public static Z0Dimension dimension;
	
	public static Logger logger;
	
	public static Config config;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();
		Z0Craft.logger.trace("trace logging activated");
		Z0Craft.logger.info("Z0Craft/preInit called");
		Z0Craft.logger.info("source file: " + event.getSourceFile());
		
		config = new Config(event.getSuggestedConfigurationFile());
		
		this.creativeTab = new Z0CreativeTab("z0craft");
		blocks.preInit(event);
		dimension.preInit(event);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		Z0Craft.logger.info("Z0Craft/init called");
		
		blocks.init(event);
		dimension.init(event);
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		Z0Craft.logger.info("Z0Craft/postInit called");
		blocks.postInit(event);
		
		dimension.postInit(event);
	}
}
