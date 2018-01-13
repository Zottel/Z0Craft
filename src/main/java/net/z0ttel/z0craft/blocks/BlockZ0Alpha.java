package net.z0ttel.z0craft.blocks;

import javax.annotation.Nullable;

import net.minecraft.block.material.Material;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.material.MapColor;


import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;

import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;

import net.z0ttel.z0craft.Z0Craft;

public class BlockZ0Alpha extends BlockBreakable {
	
	public BlockZ0Alpha(Material materialIn) {
		// "false" is ignoreSimilarity, which sets the side rendering
		// to the right mode for transparent blocks
		super(materialIn, false, MapColor.ICE);
	}
	
	public BlockZ0Alpha(Material materialIn, MapColor mapColorIn) {
		super(materialIn, false, mapColorIn);
	}
	
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.TRANSLUCENT;
	}
	
}
