package com.demo.config.jms;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

@EnableJms
@Configuration
public class JMSConfig {
    @Value(value = "${spring.activemq.broker-url}")
    String brokerUrl;

    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory() {
        var connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL (brokerUrl);
        connectionFactory.setUseAsyncSend(true);
        connectionFactory.setTrustAllPackages(true);
        return connectionFactory;
    }
    @Bean
    public JmsTemplate jmsTemplate() {
        var jmsTemplate = new JmsTemplate(new CachingConnectionFactory(activeMQConnectionFactory())); jmsTemplate.setDeliveryPersistent(false);
        return jmsTemplate;
    }
    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
        var factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(activeMQConnectionFactory());
        return factory;
    }
}