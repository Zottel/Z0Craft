package net.z0ttel.z0craft.world.structures;

import java.io.File;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.LinkedHashMap;

import net.minecraft.util.math.ChunkPos;

import net.minecraft.util.ResourceLocation;

import net.minecraft.world.World;
import net.minecraft.world.DimensionType;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraft.world.gen.structure.template.Template;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;


import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

import net.z0ttel.z0craft.Z0Craft;
import net.z0ttel.z0craft.math.Z0Random.WorldChunk;
import net.z0ttel.z0craft.util.CacheMap;

public class Z0Structures {
	public static TemplateManager templateManager = new TemplateManager("/", null);
	public static Map<String, Template> templates = new HashMap<String, Template>();
	public static Template getTemplate(String name) {
		if(!templates.containsKey(name)) {
			templates.put(name, templateManager.getTemplate(null, new ResourceLocation(name)));
		}
		
		return templates.get(name);
	}
	
	public static Map<Integer, StructureSpawner> structureSpawners =
		new HashMap<Integer, StructureSpawner>();
	
	private Map<WorldChunk, List<StructureInstance>> structCache =
		new CacheMap<WorldChunk, List<StructureInstance>>(256);
	
	public void preInit(FMLPreInitializationEvent event) {
		// TODO: load templates here
	}
	
	public void init(FMLInitializationEvent event) {
	}
	
	public void postInit(FMLPostInitializationEvent event) {
	}
	
	public void fillChunk(World world, long seed, ChunkPos chunk) {
		int dimensionId = world.provider.getDimension();
		// TODO: RESET WHEN NEW WORLD IS LOADED
		
		world.profiler.startSection("Z0Structures");
		
		if(!structureSpawners.containsKey(dimensionId)) {
			if(world.provider.getDimensionType() == DimensionType.OVERWORLD) {
				StructureSpawner newSpawner =
					new StructureSpawnerOverworld(world, structCache);
				structureSpawners.put(dimensionId, newSpawner);
				newSpawner.fillChunk(world, chunk);
			} else if(world.provider.getDimensionType() == Z0Craft.dimension.dimType) {
				StructureSpawner newSpawner =
					new StructureSpawnerOverworld(world, structCache);
				structureSpawners.put(dimensionId, newSpawner);
				newSpawner.fillChunk(world, chunk);
			} else {
				// TODO throw Exception
			}
		} else {
			structureSpawners.get(dimensionId).fillChunk(world, chunk);
		}
		
		world.profiler.endSection();
	}
}
