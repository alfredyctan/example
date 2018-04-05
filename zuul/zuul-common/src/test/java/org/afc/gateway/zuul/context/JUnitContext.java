package org.afc.gateway.zuul.context;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;

public class JUnitContext {

	@Bean
	public Mockito mockito() {
		return new Mockito();
	}

}
