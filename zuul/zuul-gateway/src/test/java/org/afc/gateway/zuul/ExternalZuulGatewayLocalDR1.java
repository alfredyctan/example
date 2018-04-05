package org.afc.gateway.zuul;

import org.afc.env.Environment;
import org.afc.jsse.JSSEProperties;
import org.afc.util.ClasspathUtil;

public class ExternalZuulGatewayLocalDR1 {

	public static void main(String[] args) throws Exception {
		Environment.set("zuul-ext", "local", "dr", "default", "dr1");
		ClasspathUtil.addSystemClasspath("target/config");
		JSSEProperties.setKeyStore("target/zuul-common-test/keystore/local/zuul.jks", "localhost");
		JSSEProperties.setTrustStore("target/zuul-common-test/keystore/local/zuul.jks", "localhost");
//		JSSEProperties.setDebug("ssl");

		ExternalZuulGateway.main(new String[] { "--spring.profiles.active=eureka-local,zuul-ext,local,dr,default,dr1" });
	}
}
