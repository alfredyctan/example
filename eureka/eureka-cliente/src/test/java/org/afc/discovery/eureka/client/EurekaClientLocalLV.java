package org.afc.discovery.eureka.client;


import org.afc.env.Environment;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class EurekaClientLocalLV implements CommandLineRunner {

	@Override
	public void run(String... arg0) throws Exception {
	}

	public static void main(String[] args) throws Exception {
		Environment.set("eureka-client", "local", "lv", "default", "lv1");
		SpringApplication.run(EurekaClientLocalLV.class, new String[] { "--spring.profiles.active=local,lv,default,lv1,eureka-local" });
	}
}
