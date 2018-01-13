package net.z0ttel.z0craft.util;

import net.minecraft.entity.Entity;

import net.minecraft.util.math.BlockPos;

public class EntityPos {
	public double x, y, z;
	public EntityPos(Entity entityIn) {
		x = entityIn.posX;
		y = entityIn.posY;
		z = entityIn.posZ;
	}
	
	public EntityPos() {
	}
	
	public BlockPos block() {
		return new BlockPos(x, y, z);
	}
}
