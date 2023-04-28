package com.demo.config.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ToString
@Setter @Getter
@ConfigurationProperties(prefix = "temporal")
public class TemporalProperties {
    private String server;
    private String namespace;
    private Boolean isUseSsl;
    private Long workflowTaskTimeoutConfig;
    private Long workflowExecutionTimeout;
    private Long defaultActivityStartToCloseTimeout;
    private Integer defaultActivityMaximumRetryAttempts;
    private Long defaultLocalActivityStartToCloseTimeout;
    private Integer defaultLocalActivityMaximumRetryAttempts;
    private Integer defaultLocalActivityRetryInterval;

    private WorkerConfig worker;
    private PrometheusConfig prometheus;
    private WorkerFactoryConfig workerFactory;

    @Setter @Getter
    public static class WorkerConfig {
        private Integer maxConcurrentActivityTaskPollers;
        private Integer maxConcurrentWorkflowTaskPollers;
        private Integer maxConcurrentActivityExecutionSize;
        private Integer maxConcurrentWorkflowTaskExecutionSize;
        private Integer maxConcurrentLocalActivityExecutionSize;
        private Integer maxWorkerActivitiesPerSecond;
        private Integer maxTaskQueueActivitiesPerSecond;
    }

    @Setter @Getter
    public static class PrometheusConfig {
        private Integer port;
        private Long sleep;
    }

    @Setter @Getter
    public static class WorkerFactoryConfig {
        private Integer maxWorkfLowThreadCount;
        private Integer workflowHostLocalPollThreadCount;
    }
//    worker:
//    maxConcurrentActivityTaskPollers: 25 #default 5
//    maxConcurrentWorkflowTaskPollers: 10 #default 2
//    maxConcurrentActivityExecutionSize: 200 #default 200
//    maxConcurrentWorkflowTaskExecutionSize: 200 #default 200
//    maxConcurrentLocalActivityExecutionSize: 200 #default 200
//    maxWorkerActivitiesPerSecond: 0 #default 0 means unlimited
//    maxTaskQueueActivitiesPerSecond: 0 #default 0 means unlimited
//    workerFactory:
//    maxWorkfLowThreadCount: 600 #defautt 600
//    workflowHostLocalPollThreadCount: 5 #default 5
//    prometheus:
//    port: 9790
//    sleep: 3000
}
