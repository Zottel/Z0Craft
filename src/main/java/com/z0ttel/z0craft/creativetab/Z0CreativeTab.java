package com.z0ttel.z0craft.creativetab;

import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.creativetab.CreativeTabs;

import net.minecraft.init.Items;

import com.z0ttel.z0craft.Z0Craft;
import com.z0ttel.z0craft.blocks.Z0Blocks;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Z0CreativeTab extends CreativeTabs {
	public Z0CreativeTab(String label) {
		super(label);
	}
	
	@SideOnly(Side.CLIENT)
	public void displayAllRelevantItems(List<ItemStack> list) {
		for(Item item: Z0Craft.blocks.blockitems) {
			item.getSubItems(item, this, list);
		}
	}
	
	
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem()
	{
		return Items.WRITTEN_BOOK;
	}
}
