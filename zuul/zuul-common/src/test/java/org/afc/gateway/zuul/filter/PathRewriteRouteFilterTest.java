package org.afc.gateway.zuul.filter;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.afc.gateway.zuul.context.JUnitContext;
import org.afc.gateway.zuul.context.LocalContext;
import org.afc.gateway.zuul.filter.PathRewriteRouteFilter;
import org.afc.util.JUnit4Util;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;


@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("junit")
@ContextConfiguration(classes= {LocalContext.class, JUnitContext.class}, initializers = { ConfigFileApplicationContextInitializer.class })
public class PathRewriteRouteFilterTest {

	@Autowired
	private PathRewriteRouteFilter filter;

	@Test
	public void testBasicInsert() {
		try {
			RequestContext context = RequestContext.getCurrentContext();
			context.put(FilterConstants.PROXY_KEY, "unit-test-basic");
			context.put(FilterConstants.REQUEST_URI_KEY, "/api/kernel/v1/labels/en");
			filter.run();
			
			String actual = JUnit4Util.actual(context.get(FilterConstants.REQUEST_URI_KEY).toString()); 
			String expect = JUnit4Util.expect("/afc/api/kernel/v1/labels/en"); 
			
			assertThat("rewrite", actual, is(expect));
			
		} catch (ZuulException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCapturingGroup() {
		try {
			RequestContext context = RequestContext.getCurrentContext();
			context.put(FilterConstants.PROXY_KEY, "unit-test-capturing-group");
			context.put(FilterConstants.REQUEST_URI_KEY, "/api/kernel/v1/labels/en");
			filter.run();
			
			String actual = JUnit4Util.actual(context.get(FilterConstants.REQUEST_URI_KEY).toString()); 
			String expect = JUnit4Util.expect("/api/legacy/labels/zh-en/v1"); 
			
			assertThat("rewrite", actual, is(expect));
			
		} catch (ZuulException e) {
			e.printStackTrace();
		}
	}	

	@Test
	public void test() {
		String from = "/user/alfred";
		System.out.println("rewrite : " + from + " -> " + from.replaceAll("\\/user\\/(?<name>.*)", "\\/$1\\/user"));
	}
}
