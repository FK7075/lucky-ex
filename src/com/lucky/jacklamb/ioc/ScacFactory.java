package com.lucky.jacklamb.ioc;

import com.lucky.jacklamb.ioc.config.Configuration;

public class ScacFactory {
	
	private static PackageScan pack;
	
	private static JarScan jar;
	
	public static Scan createScan() {
		if(PackageScan.class.getClassLoader().getResource("")==null) {
			if(jar==null)
				jar= new JarScan();//Configuration.getScanConfig().getApplication()
			return jar;	
		}else {
			if(pack==null)
				pack= new PackageScan();
			return pack;
		}
		
	}

}
