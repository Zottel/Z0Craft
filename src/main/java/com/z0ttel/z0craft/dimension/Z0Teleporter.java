package com.z0ttel.z0craft.dimension;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;
import net.minecraft.world.World;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;

import com.z0ttel.z0craft.Z0Craft;
import com.z0ttel.z0craft.util.EntityPos;

public class Z0Teleporter extends Teleporter {
	public static enum Direction {
		UP, DOWN
	};
	
	private final WorldServer worldServer;
	private Direction direction;
	private EntityPos ePos;
	
	public Z0Teleporter(WorldServer worldIn, Direction directionIn, EntityPos ePosIn) {
		super(worldIn);
		Z0Craft.logger.info("Z0Teleporter/Z0Teleporter(worldIn) called");
		
		worldServer = worldIn;
		direction = directionIn;
		ePos = ePosIn;
	}
	
	private void placeAtHeight(Entity entityIn, double height) {
		placeAt(entityIn, ePos.x, height, ePos.z);
	}
	
	private void placeAt(Entity entityIn, double x, double y, double z) {
		//double x = (double)blockpos.getX() + 0.5D;
		//double z = (double)blockpos.getZ() + 0.5D;
		//entityIn.posY
		//x = ((long) x) - 1.5; // TODO: Fix this fix into understanding.
		//z = ((long) z) - 0.5;
		
		if (entityIn instanceof EntityPlayerMP)
		{
			((EntityPlayerMP)entityIn).connection.setPlayerLocation(x, y, z, entityIn.rotationYaw, entityIn.rotationPitch);
		}
		else
		{
			entityIn.setLocationAndAngles(x, y, z, entityIn.rotationYaw, entityIn.rotationPitch);
		}
	}
	
	public void placeInPortal(Entity entityIn, float rotationYaw) {
		Z0Craft.logger.info("Z0Teleporter/placeInPortal called for entity: " + entityIn);
		
		// Do at least call the methods we don't need...
		if(!this.placeInExistingPortal(entityIn, rotationYaw))
		{
			if(this.makePortal(entityIn)) {
				this.placeInExistingPortal(entityIn, rotationYaw);
			} else {
				placeAtHeight(entityIn, 256);
			}
		}
	}
	
	public boolean placeInExistingPortal(Entity entityIn, float rotationYaw) {
		Z0Craft.logger.info("Z0Teleporter/placeInExistingPortal called");
		BlockPos.MutableBlockPos iterator =
			new BlockPos.MutableBlockPos(ePos.block());
		
		// Derp correction:
		//iterator.setPos(iterator.getX() - 1, iterator.getY(), iterator.getZ() - 1);
		
		// Count blocks of air for player to be placed in.
		// Stop when first opening of 2 or 3 blocks of air is found.
		int freeBlocks = 0;
		int step = direction == Direction.UP ? 1 : -1;
		int start = direction == Direction.UP ? 0 : 255;
		int stop = direction == Direction.UP ? 256 : -1;
		
		for(int i = start; i != stop; i += step) {
			iterator.setY(i);
			
			// Maybe try Block.isAir too?
			// (may help with mod air)
			if(worldServer.isAirBlock(iterator)) {
				freeBlocks++;
				if(freeBlocks == 3) {
					placeAtHeight(entityIn,
					              Math.min(i, i - (2 * step)));
					return true;
				}
			} else {
				if(freeBlocks == 2) {
					placeAtHeight(entityIn,
					              Math.min(i - step, i - (2 * step)));
					return true;
				}
			}
		}
		
		return false;
	}
	
	public boolean makePortal(Entity entityIn) {
		Z0Craft.logger.info("Z0Teleporter/makePortal called");
		return false;
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
