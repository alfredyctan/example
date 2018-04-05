package org.afc.discovery.eureka;

import org.afc.env.Environment;

public class EurekaServerLocalDR1 {

	public static void main(String[] args) {
		Environment.set("eureka", "local", "dr", "default", "dr1");
		EurekaServer.main(new String[] { "--spring.profiles.active=eureka-local,local,dr,default,dr1" });
	}
}
