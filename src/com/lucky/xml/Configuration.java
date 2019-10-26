package com.lucky.xml;

import com.lucky.utils.ProperConfig;

public abstract class Configuration {
	
	public ProperConfig getProperConfig() {
		ProperConfig property=new ProperConfig();
		property.setLog(false);
		return property;
	}

}
