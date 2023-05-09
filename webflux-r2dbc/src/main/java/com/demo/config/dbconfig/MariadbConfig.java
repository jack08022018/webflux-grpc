package com.demo.config.dbconfig;

import com.demo.config.properties.DatasourceProperties;
import io.r2dbc.mssql.MssqlConnectionConfiguration;
import io.r2dbc.mssql.MssqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import lombok.RequiredArgsConstructor;
import org.mariadb.r2dbc.MariadbConnectionConfiguration;
import org.mariadb.r2dbc.MariadbConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.r2dbc.ConnectionFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.core.DefaultReactiveDataAccessStrategy;
import org.springframework.data.r2dbc.core.R2dbcEntityOperations;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.core.ReactiveDataAccessStrategy;
import org.springframework.data.r2dbc.dialect.MySqlDialect;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.data.relational.core.dialect.MariaDbDialect;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
@EnableTransactionManagement
@EnableR2dbcRepositories(basePackages = "com.demo.repository.mariadb",
        databaseClientRef = "mariaDatabaseClient",
        entityOperationsRef = "mariaEntityTemplate")
public class MariadbConfig {
//public class MariadbConfig extends AbstractR2dbcConfiguration {
    final DatasourceProperties datasourceProperties;

    @Primary
    @Bean("mariaConnectionFactory")
    public MariadbConnectionFactory connectionFactory() {
        var properties = datasourceProperties.getMariadb();
        return new MariadbConnectionFactory(MariadbConnectionConfiguration.builder()
                .host(properties.getHost())
                .port(properties.getPort())
                .database(properties.getDatabase())
                .username(properties.getUser())
                .password(properties.getPassword())
                .connectTimeout(Duration.ofSeconds(properties.getTimeout()))
                .build());
    }

    @Primary
    @Bean("mariaReactiveDataAccessStrategy")
    public ReactiveDataAccessStrategy reactiveDataAccessStrategy() {
        return new DefaultReactiveDataAccessStrategy(MySqlDialect.INSTANCE);
    }

    @Primary
    @Bean("mariaTransactionManager")
    public ReactiveTransactionManager transactionManager(
            @Autowired ConnectionFactory mariaConnectionFactory) {
        return new R2dbcTransactionManager(mariaConnectionFactory);
    }

    @Primary
    @Bean("mariaDatabaseClient")
    public DatabaseClient databaseClient(@Autowired ConnectionFactory mariaConnectionFactory) {
        return DatabaseClient.create(mariaConnectionFactory);
    }

    @Primary
    @Bean("mariaEntityTemplate")
    public R2dbcEntityOperations entityTemplate(
            @Autowired ConnectionFactory mariaConnectionFactory) {
        var strategy = new DefaultReactiveDataAccessStrategy(MySqlDialect.INSTANCE);
        DatabaseClient databaseClient = DatabaseClient.builder()
                .connectionFactory(mariaConnectionFactory)
                .bindMarkers(MySqlDialect.INSTANCE.getBindMarkersFactory())
                .build();

        return new R2dbcEntityTemplate(databaseClient, strategy);
    }

}
