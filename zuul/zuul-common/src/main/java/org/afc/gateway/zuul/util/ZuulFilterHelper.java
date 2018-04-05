package org.afc.gateway.zuul.util;

import org.springframework.http.HttpStatus;

import com.netflix.zuul.ExecutionStatus;
import com.netflix.zuul.ZuulFilterResult;
import com.netflix.zuul.context.RequestContext;

public class ZuulFilterHelper {

	public static ZuulFilterResult createForbiddenZuulResponse(RequestContext context, String serviceId, String proxy) {
		ZuulFilterResult zr = new ZuulFilterResult(ExecutionStatus.FAILED);
		String message = String.format("insufficient privileges to access %s/%s", proxy, serviceId);
		zr.setException(new SecurityException(message));
		context.setResponseBody(message);
		context.setResponseStatusCode(HttpStatus.FORBIDDEN.value());
		context.setSendZuulResponse(false); // false mean stop routing after pre-filter chain is done
		return zr;
	}

}
