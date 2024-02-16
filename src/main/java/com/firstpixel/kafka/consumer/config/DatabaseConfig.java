package com.firstpixel.kafka.consumer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableJpaRepositories
@EnableTransactionManagement
public class DatabaseConfig {
	
	 	@Value("${spring.datasource.url}")
	    private String datasourceUrl;

	    @Value("${spring.datasource.username}")
	    private String username;

	    @Value("${spring.datasource.password}")
	    private String password;

	    @Value("${spring.datasource.driver-class-name}")
	    private String driverClassName;

	    @Bean
	    public HikariDataSource dataSource() {
	        HikariConfig config = new HikariConfig();
	        config.setJdbcUrl(datasourceUrl);
	        config.setUsername(username);
	        config.setPassword(password);
	        config.setDriverClassName(driverClassName);
	        // Additional HikariCP configuration if needed
	        return new HikariDataSource(config);
	    }
}