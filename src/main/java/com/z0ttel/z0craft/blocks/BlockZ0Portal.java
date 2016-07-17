package com.z0ttel.z0craft.blocks;

import javax.annotation.Nullable;

import net.minecraft.block.material.Material;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.material.MapColor;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.Teleporter;

import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;

import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;

import com.z0ttel.z0craft.Z0Craft;
import com.z0ttel.z0craft.dimension.Z0Teleporter;
import com.z0ttel.z0craft.util.EntityPos;

public class BlockZ0Portal extends BlockBreakable {

	// Bounding box for Portal blocks
	// possible alternative: larger bounding box so we collide before a player enters the block?
	//protected static final AxisAlignedBB END_PORTAL_AABB = new AxisAlignedBB(0.2D, 0.0D, 0.2D, 0.8D, 1.0D, 0.8D);
	/*public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return END_PORTAL_AABB;
	}*/
	
	// TODO: get rid of deprecated stuffs
	@SuppressWarnings( "deprecation" )
	@Nullable
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World worldIn, BlockPos pos)
	{
		return NULL_AABB;
	}
	
	
	
	public BlockZ0Portal() {
		// "false" is ignoreSimilarity, which sets the side rendering
		// to the right mode for transparent blocks
		super(Material.PORTAL, false, MapColor.EMERALD);
	}

	
	
	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
	{
		Z0Craft.logger.info("BlockZ0Portal/onEntityCollidedWithBlock called for entity: " + entityIn);
		if(entityIn.timeUntilPortal > 0) {
			return;
		}
		
		entityIn.timeUntilPortal = 20; // 1 second
		
		//entity.dimension
		int destinationDim = Z0Craft.config.dimensionID;
		if(entityIn.dimension == destinationDim) {
			destinationDim = 0;
		}
		
		if(entityIn instanceof EntityPlayerMP) {
			EntityPlayerMP player = (EntityPlayerMP)entityIn;
			// remember the exact position
			EntityPos ePos = new EntityPos(player);
			
			MinecraftServer mcServer = player.mcServer;
			
			Z0Teleporter.Direction direction =
				destinationDim == 0 ? Z0Teleporter.Direction.UP : Z0Teleporter.Direction.DOWN;
			
			Teleporter teleporter =
				new Z0Teleporter(mcServer.worldServerForDimension(destinationDim),
				                 direction,
				                 ePos);
			
			mcServer.getPlayerList().transferPlayerToDimension(player, destinationDim, teleporter);
			
			//player.connection.setPlayerLocation(ePos.x, player.posY, ePos.z, entityIn.rotationYaw, entityIn.rotationPitch);
		} else {
			// TODO: insert voodoo here
		}
	}

	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.TRANSLUCENT;
	}
	
}
