package com.demo.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter @Getter
@ConfigurationProperties(prefix = "temporal")
public class TemporalProperties {
    private String server;
    private Integer timeout;
    private WorkerConfig worker;

    @Setter @Getter
    public static class WorkerConfig {
        private String maxActivity;
    }
}
