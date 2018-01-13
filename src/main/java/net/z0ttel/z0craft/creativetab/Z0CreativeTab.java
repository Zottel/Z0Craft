package net.z0ttel.z0craft.creativetab;

import java.util.List;
import net.minecraft.util.NonNullList;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.creativetab.CreativeTabs;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import net.z0ttel.z0craft.Z0Craft;
import net.z0ttel.z0craft.blocks.Z0Blocks;
import net.z0ttel.z0craft.items.Z0Items;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Z0CreativeTab extends CreativeTabs {
	public Z0CreativeTab(String label) {
		super(label);
	}
	
	@SideOnly(Side.CLIENT)
	public void displayAllRelevantItems(NonNullList<ItemStack> list) {
		for(Item item: Z0Craft.items.items) {
			Z0Craft.logger.info("creative item: " + item.getRegistryName());
			item.getSubItems(this, list);
		}
		
		for(Item blockitem: Z0Craft.items.blockitems) {
			Z0Craft.logger.info("creative block item: " + blockitem.getRegistryName());
			blockitem.getSubItems(this, list);
		}
	}
	
	
	@SideOnly(Side.CLIENT)
	public ItemStack getTabIconItem()
	{
		return new ItemStack(Z0Craft.items.KEY);
	}
	public boolean hasSearchBar() {
		return true;
	}
}
