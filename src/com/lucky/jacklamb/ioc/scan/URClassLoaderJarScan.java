package com.lucky.jacklamb.ioc.scan;

import java.io.File;

import com.lucky.jacklamb.file.ini.INIConfig;

/**
 * ��ClassLoaderΪURLClassLoaderʱ�İ�ɨ��
 * @author fk-7075
 *
 */
public class URClassLoaderJarScan extends JarScan {

	public URClassLoaderJarScan(Class<?> clzz) {
		super(clzz);
		INIConfig ini=new INIConfig();
		jarpath=System.getProperty("user.dir")+File.separator+ini.getValue("Jar", "name");
	}
	


}
