package com.z0ttel.z0craft;

import org.apache.logging.log4j.Logger;

import net.minecraft.init.Blocks;
import net.minecraft.client.resources.I18n;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ServerSideProxy extends CommonProxy {
	public void preInit(FMLPreInitializationEvent event)
	{
		Z0Craft.logger.info("ServerSideProxy/preInit called");
		super.preInit(event);
		Z0Craft.logger.info("ServerSideProxy/preInit continuing");
	}
	
	public void init(FMLInitializationEvent event)
	{
		Z0Craft.logger.info("ServerSideProxy/init called");
		super.init(event);
		Z0Craft.logger.info("ServerSideProxy/init continuing");
	}
	
	public void postInit(FMLPostInitializationEvent event)
	{
		Z0Craft.logger.info("ServerSideProxy/postInit called");
		super.postInit(event);
		Z0Craft.logger.info("ServerSideProxy/postInit continuing");
	}
}

