package com.z0ttel.z0craft.dimension;

import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;

import com.z0ttel.z0craft.Z0Craft;

public class Z0Teleporter extends Teleporter {
	public Z0Teleporter(WorldServer worldIn) {
		super(worldIn);
		Z0Craft.logger.info("Z0Teleporter/Z0Teleporter(worldIn) called");
	}
	public void placeInPortal(Entity entityIn, float rotationYaw) {
		Z0Craft.logger.info("Z0Teleporter/placeInPortal called");
	}
	public boolean placeInExistingPortal(Entity entityIn, float rotationYaw) {
		Z0Craft.logger.info("Z0Teleporter/placeInExistingPortal called");
		return true;
	}
	public boolean makePortal(Entity entityIn) {
		Z0Craft.logger.info("Z0Teleporter/makePortal called");
		return true;
	}
	public void removeStalePortalLocations(long worldTime) {
		Z0Craft.logger.info("Z0Teleporter/removeStalePortalLocations called");
	}
	/*public class PortalPosition extends BlockPos {
		public PortalPosition() {
			Z0Craft.logger.info("Z0Teleporter/PortalPosition/PortalPosition called");
		}
	}*/
}
