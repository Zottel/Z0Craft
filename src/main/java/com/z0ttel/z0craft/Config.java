package com.z0ttel.z0craft;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

class Config{
	Configuration configFile;
	
	public int myConfigInteger = 32;
	public String myConfigString = "Hello!";
	public boolean myConfigBool = false;
	
	public Config(File file) {
		Z0Craft.logger.info("opening config file: " + file);
		configFile = new Configuration(file);
		update();
	}
	
	private void update() {
		//CATEGORY_CLIENT
		//CATEGORY_GENERAL
		myConfigInteger = configFile.getInt("My Config Integer", Configuration.CATEGORY_GENERAL, myConfigInteger, 0, Integer.MAX_VALUE, "An Integer!");
		
		myConfigString = configFile.getString("My Config String", Configuration.CATEGORY_GENERAL, myConfigString, "A String!");
		
		myConfigBool = configFile.getBoolean("My Config Bool", Configuration.CATEGORY_GENERAL, myConfigBool, "A Boolean!");
		
		if(configFile.hasChanged())
			configFile.save();
	}
	
}
