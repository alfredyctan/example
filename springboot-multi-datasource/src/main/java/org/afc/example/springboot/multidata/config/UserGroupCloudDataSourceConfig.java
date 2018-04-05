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
import org.springframework.cloud.config.java.ServiceScan;
import org.springframework.cloud.service.ServiceInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@EnableJpaRepositories(
	entityManagerFactoryRef = "userGroupEmf",
	transactionManagerRef = "userGroupTxMgr"
)
@Configuration
@ServiceScan
@Profile("cloud")
public class UserGroupCloudDataSourceConfig {

	private static final Logger logger = LoggerFactory.getLogger(UserGroupCloudDataSourceConfig.class);

	@Bean("userGroupDataSource")
    public DataSource dataSource() {
        CloudFactory cloudFactory = new CloudFactory();
        Cloud cloud = cloudFactory.getCloud();
        return cloud.getServiceConnector("usergroup-db", DataSource.class, null);
    }
	
    @Bean("userGroupEmf")
    public EntityManagerFactory userGroupEmf() {
    	Map<String, Object> config = new HashMap<String, Object>() {{
    		put("javax.persistence.nonJtaDataSource", dataSource());    		
    		put("javax.persistence.transactionType", "RESOURCE_LOCAL");
    		put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
    	}};
    	return Persistence.createEntityManagerFactory("userGroup", config);
    }

    @Bean("userGroupTxMgr")
    public PlatformTransactionManager userGroupTxMgr() {
    	return new JpaTransactionManager(userGroupEmf());
    }
}