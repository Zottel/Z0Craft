package net.z0ttel.z0craft.potion;

import net.minecraft.util.ResourceLocation;

import net.minecraft.entity.EntityLivingBase;

import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionEffect;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.RegistryEvent;

import net.z0ttel.z0craft.Z0Craft;

public class Z0Potions {
	Potion testPotion = new Z0Potion(false, 0x8B4513).setPotionName("testPotion").setRegistryName("testPotion");
	PotionType samplepotiontype = new PotionType("testPotion", new PotionEffect(testPotion, 20 * 30)).setRegistryName("testPotionType");
	
	@SubscribeEvent
	public void registerPotions(RegistryEvent.Register<Potion> event) {
		Z0Craft.logger.info("Z0Craft/preInit potion registered: " + testPotion.getRegistryName());
		//event.getRegistry().registerAll(testPotion, new ResourceLocation(Z0Craft.MODID, "potion.testPotion"));
		event.getRegistry().registerAll(testPotion);
	}
	
	@SubscribeEvent
	public void registerPotionTypes(RegistryEvent.Register<PotionType> event) {
		//event.getRegistry().registerAll(samplepotiontype, new ResourceLocation(Z0Craft.MODID, "potion.type.testPotion"));
		event.getRegistry().registerAll(samplepotiontype);
	}
	
	private class Z0Potion extends Potion {
		public Z0Potion(boolean badEffect, int color) {
			super(badEffect, color);
			setIconIndex(1, 2);
		}
		private boolean carefulRise(EntityLivingBase entity, double amount) {
			boolean collidedBefore =  !(entity.world.getCollisionBoxes(entity, entity.getEntityBoundingBox()).isEmpty());
			entity.setPosition(entity.posX, entity.posY + amount, entity.posZ);
			boolean collidedAfter = !entity.world.getCollisionBoxes(entity, entity.getEntityBoundingBox()).isEmpty();
			if(!collidedBefore && collidedAfter) {
				entity.setPosition(entity.posX, entity.posY - amount, entity.posZ);
				return false;
			} else {
				entity.onGround = false;
				return true;
			}
		}
		@Override
		public void performEffect(EntityLivingBase entity, int p_76394_2_){
			//Z0Craft.logger.info("Z0Craft.Z0Potion.performEffect called on: " + entity + " with " + p_76394_2_);
			//entity.sendMessage(new TextComponentString("You Are Performed"));
			
			entity.setInWeb();
			if(entity.onGround) {
				carefulRise(entity, 0.7);
			} else {
				carefulRise(entity, 0.01);
			}
		}
		
		@Override
		public boolean isInstant(){
			return false;
		}
		
		@Override
		public boolean isReady(int duration, int amplifier){
			return true;
		}
	}
	
}
