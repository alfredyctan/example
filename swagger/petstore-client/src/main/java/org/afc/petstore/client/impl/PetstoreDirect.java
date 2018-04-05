package org.afc.petstore.client.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.afc.logging.SDC;
import org.afc.petstore.ApiException;
import org.afc.petstore.api.UserApi;

public class PetstoreDirect {
 
	private static final Logger logger = LoggerFactory.getLogger(PetstoreDirect.class);
	
    @Autowired
	private UserApi api;
    
    @Value("${petstore.targetUrl.direct}")
    private String targetUrl;

    public void run(String... args) throws Exception
    {
    	logger.info("invoke api directly");    	
    	SDC.set(new Date());
    	for (int i = 0; i < 10; i++) {
	    	SDC.push(String.valueOf(i));
	    	
			try {
//				api.getApiClient().getHttpClient().setHostnameVerifier(new AcceptAllHostnameVerifier());
//				api.getApiClient().getHttpClient().setSslSocketFactory(SSLSocketFactoryUtil.newAcceptAll());
				api.getApiClient().setBasePath(targetUrl);
				logger.info("{} : {}, {}", api.loginUser("alfred@afc.org", "pass"), i, targetUrl);
			} catch (ApiException e) {
				e.printStackTrace();
				logger.error("code : {}", e.getCode());
				logger.error("message : {}", e.getMessage());
				logger.error("body : {}", e.getResponseBody());
			}
	    	SDC.pop();
    	}
    }
}