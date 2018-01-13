package net.z0ttel.z0craft.items;

import org.apache.logging.log4j.Logger;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraftforge.common.MinecraftForge;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.ItemMeshDefinition;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.RegistryEvent;

import net.z0ttel.z0craft.Z0Craft;

public class ClientZ0Items extends Z0Items {
	private void registerItemModel(Item item) {
		if(item instanceof Z0Map) {
			Z0Craft.logger.info("ClientZ0Items/registerItemModel for map item " + item.getUnlocalizedName() + "(" + item + ") with registry name " + item.getRegistryName().toString());
			final Item theItem = item;
			ModelLoader.setCustomMeshDefinition(item, new ItemMeshDefinition() {
						public ModelResourceLocation getModelLocation(ItemStack stack)
						{
							return new ModelResourceLocation(theItem.getRegistryName().toString(), "inventory");
						}
					});
		} else {
			Z0Craft.logger.info("ClientZ0Items/registerItemModel for item " + item.getUnlocalizedName() + "(" + item + ") with registry name " + item.getRegistryName().toString());
			ModelResourceLocation mrl = new ModelResourceLocation(item.getRegistryName().toString(), "inventory");
			ModelLoader.setCustomModelResourceLocation(item, 0, mrl);
		}
	}
	
	@SubscribeEvent
	public void registerItems(RegistryEvent.Register<Item> event) {
		super.registerItems(event);
		
		for (Item item: blockitems) {
			registerItemModel(item);
		}
		
		for (Item item: items) {
			registerItemModel(item);
		}
	}
}
