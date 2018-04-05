package org.afc.gateway.zuul.context;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.afc.gateway.zuul.context.ExtendedZuulProperties;
import org.afc.util.JUnit4Util;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("junit")
@ContextConfiguration(initializers = { ConfigFileApplicationContextInitializer.class })
@EnableConfigurationProperties({ ExtendedZuulProperties.class })
public class ExtendedZuulPropertiesTest {

	@Autowired
	private ExtendedZuulProperties properties;
	
	@Test
	public void testPatternRegexReplacement() {
		JUnit4Util.startCurrentTest(getClass());
		
		System.out.println(properties.getRoutes());

		String actual = JUnit4Util.actual(properties.getRoutes().get("unit-test").getPattern());
		String expect = JUnit4Util.expect("/(?everything)/$1/");
		assertThat("pattern", actual, is(expect));

		actual = JUnit4Util.actual(properties.getRoutes().get("unit-test").getRegex());
		expect = JUnit4Util.expect("(?everything)");
		assertThat("regex", actual, is(expect));

		actual = JUnit4Util.actual(properties.getRoutes().get("unit-test").getReplacement());
		expect = JUnit4Util.expect("$1");
		assertThat("replacement", actual, is(expect));
		
		
		JUnit4Util.endCurrentTest(getClass());
	}
}
