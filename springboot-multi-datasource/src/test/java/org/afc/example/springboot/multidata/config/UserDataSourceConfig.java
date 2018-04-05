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

@EnableJpaRepositories(
	entityManagerFactoryRef = "usersEmf",
	transactionManagerRef = "usersTxMgr"
)
@Configuration
public class UserDataSourceConfig {

    @Value("classpath:/hsqldb/users/schema/*.sql")
    private Resource[] schemas;
    
    @Value("classpath:/hsqldb/users/data/*.sql")
	private Resource[] datas;

	@Autowired
	@Qualifier("usersData")
	private DataSourceInitializer initializer;

	@Bean
    @ConfigurationProperties(prefix="datasource.users")
    public DataSource dataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean("usersData")
    public DataSourceInitializer dataSourceInitializer(final DataSource dataSource) {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(schemas);
        populator.addScripts(datas);
        
        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        initializer.setDatabasePopulator(populator);
        return initializer;
    }

    @Bean("usersEmf")
    public EntityManagerFactory userEmf() {
    	Map<String, Object> config = new HashMap<String, Object>() {{
    		put("javax.persistence.nonJtaDataSource", dataSource());    		
    		put("javax.persistence.transactionType", "RESOURCE_LOCAL");    		
    		put("hibernate.dialect", "HSQL");
    		put("jadira.usertype.autoRegisterUserTypes", "true");
    	}};
    	return Persistence.createEntityManagerFactory("users", config);
    }

    @Bean("usersTxMgr")
    public JpaTransactionManager userTxMgr() {
    	return new JpaTransactionManager(userEmf());
    }
}
