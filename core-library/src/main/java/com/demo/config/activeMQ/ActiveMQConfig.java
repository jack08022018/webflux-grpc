package com.demo.config.activeMQ;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

@EnableJms
@Configuration
@ConditionalOnProperty(value = "jms.activemq.enabled", havingValue = "true", matchIfMissing = true)
public class ActiveMQConfig {
    @Value(value = "${jms.activemq.broker-url}")
    String brokerUrl;

    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory() {
        var connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(brokerUrl);
        connectionFactory.setUseAsyncSend(true);
        connectionFactory.setTrustAllPackages(true);
        return connectionFactory;
    }
    @Bean(name = "activeMQTemplate")
    public JmsTemplate activeMQTemplate() {
        var jmsTemplate = new JmsTemplate(new CachingConnectionFactory(activeMQConnectionFactory()));
        jmsTemplate.setDeliveryPersistent(false);
        return jmsTemplate;
    }
    @Bean
    public DefaultJmsListenerContainerFactory jnsListenerContainerFactory() {
        var factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(activeMQConnectionFactory());
        return factory;
    }
}
