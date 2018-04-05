package org.afc.petstore.ssl;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.springframework.cloud.commons.httpclient.OkHttpClientFactory;

public class SSLSocketFactoryUtil {

	public static SSLSocketFactory newAcceptAll() {
		try {
			TrustManager[] tm = new TrustManager[] { new OkHttpClientFactory.DisableValidationTrustManager() };
			SSLContext context = SSLContext.getInstance("TLS");
			context.init(null, tm, null);
			return context.getSocketFactory();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
