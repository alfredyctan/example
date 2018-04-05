package org.afc.petstore.client.config;

import org.afc.petstore.api.UserApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanContext {

	@Bean
	public UserApi userApi() {
		return new UserApi();
	}
}
