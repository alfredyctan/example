package org.afc.gateway.zuul;

import org.afc.env.Environment;
import org.afc.jsse.JSSEProperties;
import org.afc.util.ClasspathUtil;

public class ExternalZuulGatewayLocalLV2 {

	public static void main(String[] args) throws Exception {
		Environment.set("zuul-ext", "local", "lv", "default", "lv1");
		ClasspathUtil.addSystemClasspath("target/config");
		JSSEProperties.setKeyStore("target/zuul-common-test/keystore/local/zuul.jks", "localhost");
		JSSEProperties.setTrustStore("target/zuul-common-test/keystore/local/zuul.jks", "localhost");
//		JSSEProperties.setDebug("ssl");

		ExternalZuulGateway.main(new String[] { "--spring.profiles.active=eureka-local,ccs-zuul-local,zuul-ext,local,lv,default,lv2" });
	}
}
