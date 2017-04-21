package com.z0ttel.z0craft.items;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockStone;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemMap;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.storage.MapData;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.z0ttel.z0craft.Z0Craft;

class Z0Map extends ItemMap {
	private void initialise(ItemStack stack, World worldIn) {
		Z0Craft.logger.info("map created");
		int meta = worldIn.getUniqueDataId("map");
		Z0Craft.logger.info("map id: " + meta);
		this.setDamage(stack, meta);
		String s = "map_" + this.getDamage(stack);
		MapData mapdata = new MapData(s);
		worldIn.setData(s, mapdata);
		mapdata.scale = 1;
		mapdata.calculateMapCenter(0, 0, mapdata.scale);
		mapdata.dimension = worldIn.provider.getDimension();
		mapdata.trackingPosition = true;
		mapdata.unlimitedTracking = true;
		mapdata.markDirty();

		MapColor colors[] = {MapColor.RED, MapColor.GREEN};

		for(int vertical = 0; vertical < 128; ++vertical) {
			for(int horizontal = 0; horizontal < 128; ++horizontal) {
				mapdata.colors[vertical*128 + horizontal] = (byte) colors[vertical % 2].colorIndex;
			}
		}
	}

	// no updates to map!
	public void updateMapData(World worldIn, Entity viewer, MapData data) {}

	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		// Not used by creative tab.
		initialise(stack, worldIn);
	}

	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		tooltip.add("z0map no. " + this.getDamage(stack));
	}
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if (!worldIn.isRemote) {
			if(this.getDamage(stack) == 0) {
				initialise(stack, worldIn);
			}

			super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
		}
	}



}
