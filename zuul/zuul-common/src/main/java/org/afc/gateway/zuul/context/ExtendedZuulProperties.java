package org.afc.gateway.zuul.context;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import org.afc.AFCException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.StringUtils;

@ConfigurationProperties("zuul")
public class ExtendedZuulProperties {

	private static final Logger logger = LoggerFactory.getLogger(ExtendedZuulProperties.class);
	
	private static final Pattern PATTERN = Pattern.compile("(/)(?<regex>.*)(?<=[^\\\\])(/)(?<replacement>.*)(/)");
	
	private Map<String, ZuulRouteExtension> routes = new LinkedHashMap<>();

	@PostConstruct
	public void init() {
		try {
		for (Entry<String, ZuulRouteExtension> entry : this.routes.entrySet()) {
			ZuulRouteExtension value = entry.getValue();
			if (!StringUtils.hasText(value.getId())) {
				value.id = entry.getKey();
			}
			if (StringUtils.hasText(value.getPattern())) {
				Matcher matcher = PATTERN.matcher(value.getPattern());
				if (matcher.find()) {
					value.setRegex(matcher.group("regex"));
					value.setReplacement(matcher.group("replacement"));
					logger.info("compiled pattern for route [{}], regex : [{}], replacement : [{}]", value.id, value.regex, value.replacement);
				} else {
					throw new AFCException("invalid path rewrite pattern " + value.pattern);
				}
			}
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static class ZuulRouteExtension {

		private String id;

		private String pattern;

		private String regex;

		private String replacement;

		private boolean secure;

		public ZuulRouteExtension() {
			this.secure = true;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getPattern() {
			return pattern;
		}

		public void setPattern(String pattern) {
			this.pattern = pattern;
		}

		public String getRegex() {
			return regex;
		}
		
		public void setRegex(String regex) {
			this.regex = regex;
		}
		
		public String getReplacement() {
			return replacement;
		}
		
		public void setReplacement(String replacement) {
			this.replacement = replacement;
		}

		public void setSecure(boolean secure) {
			this.secure = secure;
		}
		
		public boolean isSecure() {
			return secure;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			ZuulRouteExtension that = (ZuulRouteExtension) o;
			return Objects.equals(id, that.id) && Objects.equals(pattern, that.pattern) && Objects.equals(secure, that.secure);
		}

		@Override
		public int hashCode() {
			return Objects.hash(id, pattern, secure);
		}
	}

	public Map<String, ZuulRouteExtension> getRoutes() {
		return routes;
	}

	public void setRoutes(Map<String, ZuulRouteExtension> routes) {
		this.routes = routes;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExtendedZuulProperties other = (ExtendedZuulProperties) obj;
		if (routes == null) {
			if (other.routes != null)
				return false;
		} else if (!routes.equals(other.routes))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((routes == null) ? 0 : routes.hashCode());
		return result;
	}

}

