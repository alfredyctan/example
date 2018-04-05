package org.afc.gateway.zuul.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import org.afc.gateway.zuul.context.ExtendedZuulProperties;
import org.afc.gateway.zuul.context.ExtendedZuulProperties.ZuulRouteExtension;
import org.afc.util.StringUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

public class PathRewriteRouteFilter extends ZuulFilter {

	private static final Logger logger = LoggerFactory.getLogger(PathRewriteRouteFilter.class);

	@Autowired
	private ExtendedZuulProperties properties;

	@Override
	public Object run() throws ZuulException {
		RequestContext context = RequestContext.getCurrentContext();
		String proxy = context.get(FilterConstants.PROXY_KEY).toString();
		
		ZuulRouteExtension route = properties.getRoutes().get(proxy);
		if (route == null) {
			return null;
		}

		String pattern = route.getPattern();
		if (StringUtil.hasNoValue(pattern)) {
			return null;
		}
		String requestUri = context.get(FilterConstants.REQUEST_URI_KEY).toString();
		String mappedUri = requestUri.replaceAll(route.getRegex(), route.getReplacement()); 
		logger.debug("path rewrote : {} -> {} ", requestUri, mappedUri);
		context.put(FilterConstants.REQUEST_URI_KEY, mappedUri);
		return null;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public String filterType() {
		return "route";
	}

	@Override
	public int filterOrder() {
		return 99;
	}
}
