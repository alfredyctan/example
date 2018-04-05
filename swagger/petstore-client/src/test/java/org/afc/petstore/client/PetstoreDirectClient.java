package org.afc.petstore.client;

import java.util.Date;

import org.afc.logging.SDC;
import org.afc.petstore.client.impl.PetstoreDirect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("org.afc.petstore.client.config")
public class PetstoreDirectClient implements CommandLineRunner {
 
	private static final Logger logger = LoggerFactory.getLogger(PetstoreDirectClient.class);
	
    public static void main(String[] args) throws Exception {
    	SDC.set(new Date());
        SpringApplication.run(PetstoreDirectClient.class, new String[] {"--spring.profiles.active=local,hk1"});
    }

    @Autowired
    private PetstoreDirect client;
    
    @Override
    public void run(String... args) throws Exception
    {
    	client.run(args);
    }
    
    @Bean
    public PetstoreDirect client() {
    	return new PetstoreDirect(); 
    }
}