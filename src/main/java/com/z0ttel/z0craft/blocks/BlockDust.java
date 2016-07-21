package com.z0ttel.z0craft.blocks;

import javax.annotation.Nullable;

import com.z0ttel.z0craft.Z0Craft;
import com.z0ttel.z0craft.dimension.Z0Teleporter;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.BlockColored;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockDust extends BlockBreakable {

	protected static final AxisAlignedBB DUST_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.06125D, 1.0D);
	
	public BlockDust() {
		// "false" is ignoreSimilarity, which sets the side rendering
		// to the right mode for transparent blocks
		super(Material.GLASS, false);
	}
	
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return DUST_AABB;
	}
	
	// TODO: get rid of deprecated stuffs
	@SuppressWarnings("deprecation")
	@Nullable
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World worldIn, BlockPos pos)
	{
		return DUST_AABB;
	}
	
	public boolean isPassable(IBlockAccess worldIn, BlockPos pos)
	{
		return true;
	}
	
	/**
	 * Used to determine ambient occlusion and culling when rebuilding chunks for render
	 */
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
	{
		IBlockState iblockstate = worldIn.getBlockState(pos.down());
		Block block = iblockstate.getBlock();
		return block != Blocks.ICE && block != Blocks.PACKED_ICE ? (iblockstate.getBlock().isLeaves(iblockstate, worldIn, pos.down()) ? true : iblockstate.isOpaqueCube() && iblockstate.getMaterial().blocksMovement()) : false;
	}

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
	{
		for (int k = 0; k < 16; ++k)
		{
			IBlockState blockstate = Blocks.WOOL.getStateFromMeta(0);
			Z0Craft.logger.info("state id" + Block.getStateId(blockstate));
			worldIn.spawnParticle(EnumParticleTypes.BLOCK_DUST, pos.getX() + (double)this.RANDOM.nextFloat(), pos.getY() + 0.15D, pos.getZ() + (double)this.RANDOM.nextFloat(), ((double)this.RANDOM.nextFloat() - 0.5D) * 0.08D, ((double)this.RANDOM.nextFloat() - 0.5D) * 0.08D, ((double)this.RANDOM.nextFloat() - 0.5D) * 0.08D, new int[]{Block.getStateId(blockstate)});
		}
		if(!worldIn.isRemote){
			worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
		}
	}

	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.TRANSLUCENT;
	}



}
