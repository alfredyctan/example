package org.afc.example.springboot.multidata.config;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@EnableJpaRepositories(
	entityManagerFactoryRef = "userGroupEmf",
	transactionManagerRef = "userGroupTxMgr"
)
@Configuration
public class UserGroupDataSourceConfig {

    @Value("classpath:/hsqldb/userGroup/schema/*.sql")
    private Resource[] schemas;
    
    @Value("classpath:/hsqldb/userGroup/data/*.sql")
	private Resource[] datas;

	@Autowired
	@Qualifier("userGroupData")
	private DataSourceInitializer initializer;
    
	@Bean
    @ConfigurationProperties(prefix="datasource.userGroup")
    public DataSource dataSource() {
         return DataSourceBuilder.create().build();
    }

    @Bean("userGroupData")
    public DataSourceInitializer dataSourceInitializer() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(schemas);
        populator.addScripts(datas);

        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource());
        initializer.setDatabasePopulator(populator);
        return initializer;
    }

    @Bean("userGroupEmf")
    public EntityManagerFactory userGroupEmf() {
    	Map<String, Object> config = new HashMap<String, Object>() {{
    		put("javax.persistence.nonJtaDataSource", dataSource());    		
    		put("javax.persistence.transactionType", "RESOURCE_LOCAL");
    		put("jadira.usertype.autoRegisterUserTypes", "true");
    		put("hibernate.dialect", "HSQL");
    	}};
    	return Persistence.createEntityManagerFactory("userGroup", config);
    }

    @Bean("userGroupTxMgr")
    public PlatformTransactionManager userGroupTxMgr() {
    	return new JpaTransactionManager(userGroupEmf());
    }
}
