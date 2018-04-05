package org.afc.petstore.config;

import javax.servlet.Filter;

import org.afc.logging.SDCFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("org.afc.swagger.docs")
public class BeanContext {

	@Bean
	public Filter sdcFilter() {
		return new SDCFilter();
	}
}
