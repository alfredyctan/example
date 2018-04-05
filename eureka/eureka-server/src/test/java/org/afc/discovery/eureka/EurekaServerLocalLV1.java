package org.afc.discovery.eureka;

import org.afc.env.Environment;

public class EurekaServerLocalLV1 {

	public static void main(String[] args) {
		Environment.set("eureka", "local", "lv", "default", "lv1");
		EurekaServer.main(new String[] { "--spring.profiles.active=eureka-local,local,lv,default,lv1" });
	}
}
