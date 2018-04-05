package org.afc.example.springboot.multidata.config;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.Cloud;
import org.springframework.cloud.CloudFactory;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.cloud.service.ServiceInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;

@EnableJpaRepositories(
	entityManagerFactoryRef = "usersEmf",
	transactionManagerRef = "usersTxMgr"
)
@Configuration
@Profile("cloud")
public class UsersCloudDataSourceConfig extends AbstractCloudConfig {

	private static final Logger logger = LoggerFactory.getLogger(UsersCloudDataSourceConfig.class);
	
	@Bean("usersDataSource")
    public DataSource dataSource() {
        CloudFactory cloudFactory = new CloudFactory();
        Cloud cloud = cloudFactory.getCloud();
        return cloud.getServiceConnector("users-db", DataSource.class, null);
    }
    
    @Bean("usersEmf")
    public EntityManagerFactory usersEmf() {
    	Map<String, Object> config = new HashMap<String, Object>() {{
    		put("javax.persistence.nonJtaDataSource", dataSource());    		
    		put("javax.persistence.transactionType", "RESOURCE_LOCAL");    		
    		put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
    	}};
    	return Persistence.createEntityManagerFactory("users", config);
    }

    @Bean("usersTxMgr")
    public JpaTransactionManager usersTxMgr() {
    	return new JpaTransactionManager(usersEmf());
    }
}
