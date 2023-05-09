package com.demo.config;

import com.demo.config.properties.DatasourceProperties;
import com.demo.entity.ActorEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Properties;

@Configuration
@RequiredArgsConstructor
@EnableJpaRepositories(basePackages = "com.demo.repository",
        entityManagerFactoryRef = "entityManagerFactory",
        transactionManagerRef= "transactionManager")
public class DatabaseConfig {
    final DatasourceProperties datasourceProperties;

    @Bean(name = "dataSource")
    @ConfigurationProperties("datasource.mariadb.configuration")
    public DataSource getDataSource() {
        var properties = datasourceProperties.getMariadb();
        return DataSourceBuilder.create()
                .url(properties.getUrl())
                .username(properties.getUsername())
                .password(properties.getPassword())
                .build();
    }

//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//        var em = new LocalContainerEntityManagerFactoryBean();
//        em.setDataSource(getDataSource());
//        em.setPackagesToScan("com.demo.entity");
//        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
//        em.setJpaProperties(jpaProperties());
//        return em;
//    }
//
//    @Bean
//    public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
//        return new JpaTransactionManager(emf);
//    }
//
//    private Properties jpaProperties() {
//        var props = new Properties();
//        props.setProperty("hibernate.dialect", "org.hibernate.dialect.MariaDB103Dialect");
//        props.setProperty("hibernate.show_sql", "true");
//        return props;
//    }

    @Bean("entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean customEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            final @Qualifier("dataSource")
            DataSource dataSource) {
        var properties = new HashMap<String, Object>();
        properties.put("hibernate.dialect", datasourceProperties.getMariadb().getDialect());
        return builder
                .dataSource(dataSource)
                .packages(ActorEntity.class)
                .properties(properties)
                .build();
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager getTransactionManager(
            final @Qualifier("entityManagerFactory")
            LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory.getObject());
    }

}
