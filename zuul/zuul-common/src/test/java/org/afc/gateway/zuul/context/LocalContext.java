package org.afc.gateway.zuul.context;

import org.afc.gateway.zuul.filter.DebugLogFilter;
import org.afc.gateway.zuul.filter.PathRewriteRouteFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.zuul.filters.SimpleRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.post.LocationRewriteFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({ ExtendedZuulProperties.class, ZuulProperties.class})
public class LocalContext {

    @Bean
	public DebugLogFilter preDebugLogFilter() {
		return new DebugLogFilter("pre", 0);
	}

    @Bean
	public DebugLogFilter postDebugLogFilter() {
		return new DebugLogFilter("post", 99);
	}

    @Bean
	public PathRewriteRouteFilter pathRewriteRouteFilter() {
		return new PathRewriteRouteFilter();
	}
    
    @Bean
    public LocationRewriteFilter locationRewriteFilter() {
        return new LocationRewriteFilter();
    }

    @Bean
	@ConditionalOnMissingBean(SimpleRouteLocator.class)
	public SimpleRouteLocator simpleRouteLocator(ZuulProperties zuulProperties) {
		return new SimpleRouteLocator("", zuulProperties);
	}
}
