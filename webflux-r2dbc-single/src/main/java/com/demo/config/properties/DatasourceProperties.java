package com.demo.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;

@Setter @Getter
@ConfigurationProperties(prefix = "datasource")
public class DatasourceProperties {
    private DBConfig mssql;
    private DBConfig mariadb;
    private DBConfig oracle;

    @Setter @Getter
    public static class DBConfig {
        private String host;
        private Integer port;
        private String database;
        private String user;
        private String password;
        private Long timeout;
    }

    @PostConstruct
    public void init() {
        System.out.println("a");
    }
}
