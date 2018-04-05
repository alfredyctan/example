package org.afc.gateway.zuul.filter;

import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.util.Pair;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

public class DebugLogFilter extends ZuulFilter {

	private static final Logger logger = LoggerFactory.getLogger(DebugLogFilter.class);

	/* can be "error", "post", "pre", "route" */
	private String filterType;

	private int filterOrder;

	public DebugLogFilter(String filterType, int filterOrder) {
		this.filterType = filterType;
		this.filterOrder = filterOrder;
	}
	
	@Override
	public Object run() throws ZuulException {
		HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
		HttpServletResponse response = RequestContext.getCurrentContext().getResponse();

		for (Map.Entry<String, Object> entry : RequestContext.getCurrentContext().entrySet()) {
			logger.info("{}.{} - context:[{}]=[{}] ({})", filterType, filterOrder, entry.getKey(), entry.getValue(), entry.getValue().getClass().getName());
		}

		for (Enumeration<String> headers = request.getHeaderNames(); headers.hasMoreElements(); ) {
			String header = headers.nextElement();
			logger.info("{}.{} - request:[{}]=[{}]", filterType, filterOrder, header, request.getHeader(header));
		}
		
		for (String header : response.getHeaderNames()) {
			logger.info("{}.{} - response:[{}]=[{}]", filterType, filterOrder, header, response.getHeader(header));
		}
		
		Map<String, String> zuulRequests = RequestContext.getCurrentContext().getZuulRequestHeaders();
		for (Map.Entry<String, String> entry : zuulRequests.entrySet()) {
			logger.info("{}.{} - zuul request:[{}]=[{}]", filterType, filterOrder, entry.getKey(), entry.getValue());
		}

		List<Pair<String, String>> zuulResponses = RequestContext.getCurrentContext().getZuulResponseHeaders();
		for (Pair<String, String> entry : zuulResponses) {
			logger.info("{}.{} - zuul response:[{}]=[{}]", filterType, filterOrder, entry.first(), entry.second());
		}
		
		return null;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public int filterOrder() {
		return filterOrder;
	}

	@Override
	public String filterType() {
		return filterType;
	}

}
