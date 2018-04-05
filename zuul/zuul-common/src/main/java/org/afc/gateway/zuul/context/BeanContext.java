package org.afc.gateway.zuul.context;

import org.afc.gateway.zuul.filter.PathRewriteRouteFilter;
import org.afc.gateway.zuul.filter.SecuredLocationRewriteFilter;
import org.afc.spring.MutualHandshakeApacheHttpClientConnectionManagerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.commons.httpclient.ApacheHttpClientConnectionManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@ComponentScan(
	basePackages = { "org.afc.logging" }
)
@EnableConfigurationProperties({ ExtendedZuulProperties.class })
public class BeanContext {

	@Bean
	public PathRewriteRouteFilter pathMappingRouteFilter() {
		return new PathRewriteRouteFilter();
	}

	@Bean
	public ApacheHttpClientConnectionManagerFactory connManFactory() {
		return (ApacheHttpClientConnectionManagerFactory)new MutualHandshakeApacheHttpClientConnectionManagerFactory();
	}

	@Bean
	@Profile({ "!local" })
	public SecuredLocationRewriteFilter locationSSLRewriteFilter() {
		return new SecuredLocationRewriteFilter();
	}
}
