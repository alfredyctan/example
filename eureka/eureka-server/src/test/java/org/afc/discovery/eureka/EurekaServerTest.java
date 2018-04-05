package org.afc.discovery.eureka;

import org.afc.util.ClasspathUtil;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("local,one")
public class EurekaServerTest {

	@BeforeClass
	public static void setupBeforeClass( ) {
		ClasspathUtil.addSystemClasspath("target/test-classes/local");
		ClasspathUtil.addSystemClasspath("target/config");
	}
	
	@Test
	public void contextLoads() {
	}

}
