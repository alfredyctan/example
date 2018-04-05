package org.afc.gateway.zuul.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.post.LocationRewriteFilter;
import org.springframework.web.util.UriComponentsBuilder;

import com.netflix.util.Pair;
import com.netflix.zuul.context.RequestContext;

public class SecuredLocationRewriteFilter extends LocationRewriteFilter {

	private static final Logger logger = LoggerFactory.getLogger(SecuredLocationRewriteFilter.class);

	private static final String LOCATION_HEADER = "Location";

	
	public SecuredLocationRewriteFilter() {
		logger.info("{} enabled, all 3xx redirected url will be replaced with https", getClass().getName());
	}

	@Override
	public Object run() {
		super.run();
		RequestContext ctx = RequestContext.getCurrentContext();
		Pair<String, String> locationHeader = locationHeader(ctx);
		if (locationHeader != null) {
			String location = locationHeader.second();
			String modifiedLocation = UriComponentsBuilder.fromUriString(location).scheme("https").build().toUriString();
			locationHeader.setSecond(modifiedLocation);
		}
		return null;
	}

	private Pair<String, String> locationHeader(RequestContext ctx) {
		if (ctx.getZuulResponseHeaders() != null) {
			for (Pair<String, String> pair : ctx.getZuulResponseHeaders()) {
				if (pair.first().equals(LOCATION_HEADER)) {
					return pair;
				}
			}
		}
		return null;
	}
}