package com.example.config;

import com.example.entity.Knapsack;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PreDestroy;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(basePackageClasses = Knapsack.class)
@EnableTransactionManagement
public class JpaDataConfiguration {

    @Bean(destroyMethod = "close")
    DataSource dataSource(Environment env){
        HikariConfig dataSourceConfig = new HikariConfig();
        dataSourceConfig.setDriverClassName(env.getRequiredProperty("spring.datasource.driverClassName"));
        dataSourceConfig.setJdbcUrl(env.getRequiredProperty("spring.datasource.url"));
        dataSourceConfig.setUsername(env.getRequiredProperty("spring.datasource.username"));
        dataSourceConfig.setPassword(env.getRequiredProperty("spring.datasource.password"));

        return new HikariDataSource(dataSourceConfig);
    }
    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, Environment env){
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setPackagesToScan("com.example.entity");

        Properties jpaProperties = new Properties();

        //Set the diaclect
        jpaProperties.put("hibernate.dialect", env.getRequiredProperty("hibernate.dialect"));
        // set naming stratergy
        jpaProperties.put("hibernate.ejb.naming_strategy", env.getRequiredProperty("hibernate.ejb.naming_strategy"));
        // Show SQL
        jpaProperties.put("hibernate.show_sql", env.getRequiredProperty("hibernate.show_sql"));
        // FOrmat SQl
        jpaProperties.put("hibernate.format_sql", env.getRequiredProperty("hibernate.format_sql"));
//        // LOB Creation
//        jpaProperties.put("hibernate.jdbc.lob.non_contextual_creation", env.getRequiredProperty("hibernate.jdbc.lob.non_contextual_creation"));
//        // metadata defaults
//        jpaProperties.put("hibernate.temp.use_jdbc_metadata_defaults", env.getRequiredProperty("hibernate.temp.use_jdbc_metadata_defaults"));
        return entityManagerFactoryBean;
    }

    @Bean
    JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory){
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);
        return jpaTransactionManager;
    }

    @PreDestroy
    public void destroy() {
        System.out.println(
                "Callback triggered - @PreDestroy.");
    }
}
