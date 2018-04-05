package org.afc.discovery.eureka;

import org.afc.env.Environment;

public class EurekaServerLocalDR2 {

	public static void main(String[] args) {
		Environment.set("eureka", "local", "dr", "default", "dr2");
		EurekaServer.main(new String[] { "--spring.profiles.active=eureka-local,local,dr,default,dr2" });
	}
}
