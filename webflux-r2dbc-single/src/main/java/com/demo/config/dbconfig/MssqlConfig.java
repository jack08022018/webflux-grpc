package com.demo.config.dbconfig;

import com.demo.config.properties.DatasourceProperties;
import io.r2dbc.mssql.MssqlConnectionConfiguration;
import io.r2dbc.mssql.MssqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.core.DefaultReactiveDataAccessStrategy;
import org.springframework.data.r2dbc.core.R2dbcEntityOperations;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.dialect.MySqlDialect;
import org.springframework.data.r2dbc.dialect.SqlServerDialect;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
@EnableTransactionManagement
@EnableR2dbcRepositories(basePackages = "com.demo.repository",
        databaseClientRef = "databaseClient",
        entityOperationsRef = "entityTemplate")
@ConditionalOnProperty(name = "datasource.mssql.enabled", havingValue = "true")
public class MssqlConfig extends AbstractR2dbcConfiguration {
    final DatasourceProperties datasourceProperties;

    @Bean
    public MssqlConnectionFactory connectionFactory() {
        var properties = datasourceProperties.getMssql();
        return new MssqlConnectionFactory(MssqlConnectionConfiguration.builder()
                .host(properties.getHost())
                .port(properties.getPort())
                .database(properties.getDatabase())
                .username(properties.getUser())
                .password(properties.getPassword())
                .connectTimeout(Duration.ofSeconds(properties.getTimeout()))
                .build());
    }

    @Bean("transactionManager")
    public ReactiveTransactionManager transactionManager(
            @Autowired ConnectionFactory connectionFactory) {
        return new R2dbcTransactionManager(connectionFactory);
    }

    @Bean("databaseClient")
    public DatabaseClient databaseClient(@Autowired ConnectionFactory connectionFactory) {
        return DatabaseClient.create(connectionFactory);
    }

    @Bean("entityTemplate")
    public R2dbcEntityOperations entityTemplate(
            @Autowired ConnectionFactory connectionFactory) {
        var strategy = new DefaultReactiveDataAccessStrategy(SqlServerDialect.INSTANCE);
        DatabaseClient databaseClient = DatabaseClient.builder()
                .connectionFactory(connectionFactory)
                .bindMarkers(MySqlDialect.INSTANCE.getBindMarkersFactory())
                .build();

        return new R2dbcEntityTemplate(databaseClient, strategy);
    }

}
