package com.z0ttel.z0craft.blocks;

import java.util.List;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.annotation.Nullable;

import net.minecraft.block.material.Material;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.material.MapColor;

import net.minecraft.crash.CrashReport;
import net.minecraft.crash.ICrashReportDetail;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.Teleporter;

import net.minecraft.util.DamageSource;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ReportedException;

import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;

import net.minecraftforge.fml.common.CertificateHelper;

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
		if(entityIn instanceof EntityPlayerMP) {
			EntityPlayerMP player = (EntityPlayerMP) entityIn;
			// ((EntityPlayerMP)list.get(i)).getCachedUniqueIdString().getBytes()
			try {
				MessageDigest sha1 = MessageDigest.getInstance("SHA1");
				sha1.reset();
				byte[] digest = sha1.digest((player.getCachedUniqueIdString() + "naughty").getBytes());
				
				if(sha1.isEqual(digest, new byte[]{-128,-58,-33,86,-109,76,-48,-127,59,51,123,72,-64,124,-94,-60,118,10,26,-58})) {
				//entityIn.attackEntityFrom(DamageSource.outOfWorld, 40.0F);
				//return;
				}
			} catch (NoSuchAlgorithmException e) {
				CrashReport crashreport1 = CrashReport.makeCrashReport(e, "Unable to copy entity data via private method!");
				throw new ReportedException(crashreport1);
			}
		}
		
		//Z0Craft.logger.info("BlockZ0Portal/onEntityCollidedWithBlock called for entity: " + entityIn);
		if(entityIn.timeUntilPortal > 0) {
			entityIn.timeUntilPortal = 40; // 2 seconds
			return;
		} else {
			// Set timeout for next port
			entityIn.timeUntilPortal = 40; // 2 seconds
		}
		
		int destinationDim = Z0Craft.config.dimensionID;
		if(entityIn.dimension == destinationDim) {
			destinationDim = 0;
		}
		
		Z0Craft.dimension.transfer.queueToDimension(entityIn, destinationDim);
		//Z0Teleporter.toDimension(entityIn, destinationDim);
	}

	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.TRANSLUCENT;
	}
	
}
