package net.z0ttel.z0craft.world.structures;

import java.util.List;
import java.util.LinkedList;

import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.BlockPos;

import net.minecraft.util.Rotation;

import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.StructureBoundingBox;

import net.z0ttel.z0craft.Z0Craft;
import net.z0ttel.z0craft.math.Z0Random;
import net.z0ttel.z0craft.math.Z0Random.WorldChunk;

//new StructurePlacement(2, 4).in(4,4).of(new Structure("z0craft:wolleteil")).seededWith(1);

class Structure {
	private String name = "z0craft:wolleteil";
	private Template templateTop;
	private Template templateBottom;
	
	public Structure(String templateName) {
		this.name = templateName;
		templateTop = Z0Structures.getTemplate("z0craft:entry_top");
		templateBottom = Z0Structures.getTemplate("z0craft:entry_bottom");
	}
	
	// Z0Random is deterministic, so every structure generated for this chunk
	// should always be exactly the same. (While code stays the same)
	public List<StructureInstance> forChunk(World worldIn, ChunkPos chunk) {
		LinkedList<StructureInstance> structs = new LinkedList<StructureInstance>();
		ChunkPos superChunk = new ChunkPos(chunk.x / 4, chunk.z / 4);
		Z0Random rand = new Z0Random(worldIn.getSeed(), superChunk);
		if(rand.nextInt(4) == (Math.abs(chunk.x) % 4) &&
		   rand.nextInt(4) == (Math.abs(chunk.z) % 4)) {
			int dimensionId = worldIn.provider.getDimension();
			int layer = ((dimensionId == Z0Craft.config.dimensionID) ? 251 : 0);
			Template template =
				((dimensionId == Z0Craft.config.dimensionID) ? templateBottom : templateTop);
			
			BlockPos pos = chunk.getBlock(rand.nextInt(16),
			                              layer,
			                              rand.nextInt(16));
			structs.add(new StructureInstance(worldIn, pos, rand, template));
		}
		
		/*
		Z0Random rand = new Z0Random(worldIn, chunk);
		int num = rand.nextInt(6); // maximum of 5 elements
		//Z0Craft.logger.info("chunk with " + num + "features: " + chunk);
		
		for(int i=0; i<num; i++){
			// TODO: 200 => 256 - template height;
			BlockPos pos = chunk.getBlock(rand.nextInt(16),
			                              0,
			                              rand.nextInt(16));
			structs.add(new StructureInstance(worldIn, pos, rand, template));
		}*/
		
		return structs;
	}
	
	// Per-placement seed!
	public StructureInstance place(long seed, int x, int y) {
		return null;
		// TODO: to z or not to z
	}
}
