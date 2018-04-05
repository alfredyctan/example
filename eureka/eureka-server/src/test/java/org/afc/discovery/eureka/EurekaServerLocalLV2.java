package org.afc.discovery.eureka;

import org.afc.env.Environment;

public class EurekaServerLocalLV2 {

	public static void main(String[] args) {
		Environment.set("eureka", "local", "lv", "default", "lv2");
		EurekaServer.main(new String[] { "--spring.profiles.active=eureka-local,local,lv,default,lv2" });
	}
}
