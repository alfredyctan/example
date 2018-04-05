package org.afc.petstore.client;


import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.afc.petstore.client.impl.PetstoreDirect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("org.afc.petstore.client.config")
public class PetstoreClient implements CommandLineRunner {
 
	private static final Logger logger = LoggerFactory.getLogger(PetstoreClient.class);
	
    public static void main(String[] args) throws Exception {
        SpringApplication app = new SpringApplication(PetstoreClient.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
//        SpringApplication.run(PetstoreClientLocal.class, args);
    }

    @Autowired
    private PetstoreDirect direct;

    @Override
    public void run(String... args) throws Exception {
    	String cmd = null;
    	BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Direct (d), Eureka (e), Ribbon (r), Zuul (z) ?");
    	while(!(cmd = stdin.readLine()).equals("exit")) {
    		System.out.println("Direct (d), Eureka (e), Ribbon (r), Zuul (z) ?");
    		if ("d".equalsIgnoreCase(cmd)) {
    			direct.run(args);
    		} else {
    			System.out.println("not understand");
    		}
    	}
    }

    @Bean
    public PetstoreDirect petstoreDirect() {
    	return new PetstoreDirect(); 
    }

}
