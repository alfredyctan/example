package org.afc.petstore;

import org.afc.env.Environment;
import org.afc.util.ClasspathUtil;

public class PetstoreLocal {

	public static void main(String[] args) throws Exception {
		Environment.set("petstore", "local", "vi", "default", "vi1");
		ClasspathUtil.addSystemClasspath("target/config");
		Petstore.main(new String[] {"--spring.profiles.active=local,default,vi,vi1"});
	}

}
