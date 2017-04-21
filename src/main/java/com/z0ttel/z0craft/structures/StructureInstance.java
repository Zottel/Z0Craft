package com.z0ttel.z0craft.structures;

import java.util.List;
import java.util.LinkedList;

import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.BlockPos;

import net.minecraft.util.Rotation;

import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.StructureBoundingBox;

import com.z0ttel.z0craft.Z0Craft;
import com.z0ttel.z0craft.util.Z0Random;

class StructureInstance {
	private World world;
	private BlockPos origin;
	private PlacementSettings placement;
	
	private Template template;
	
	public StructureInstance(World worldIn, BlockPos originIn, Z0Random rand, Template templateIn) {
		template = templateIn;
		world = worldIn;
		origin = originIn;
		template = templateIn;
		// Choose a random rotation.
		Rotation rot = Rotation.values()[rand.nextInt(Rotation.values().length)];
		placement = new PlacementSettings();
		placement.setRotation(rot);
	}
	
	// Expects the world again because we might encounter different worlds for
	// the same seed/dimension in single player.
	public void fillChunk(World world, ChunkPos chunk) {
		//Z0Craft.logger.info("filling chunk with structure: " + chunk + " at " + origin);
		// Only draw parts of the structure that are in the current chunk.
		placement.setBoundingBox(null);
		placement.setChunk(chunk);
		// getter regenerates bounds for current chunk
		StructureBoundingBox bounds = placement.getBoundingBox();
		
		template.addBlocksToWorld(this.world, origin, placement);
	}
}
