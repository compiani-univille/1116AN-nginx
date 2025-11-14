package com.example.demo1.messaging;

import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String EXCHANGE_NAME = "auth";

    //Queues
    public static final String NOTIFICATION_QUEUE_NAME = "notification";
    public static final String CUPOM_QUEUE_NAME = "cupom";

    // Routing Keys
    public static final String USER_CREATED_ROUTING_KEY = "auth.user.created";
    public static final String USER_UPDATED_ROUTING_KEY = "auth.user.updated";

    @Bean
    Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    SimpleRabbitListenerContainerFactory container(
        ConnectionFactory connectionFactory,
        Jackson2JsonMessageConverter messageConverter
    ) {
        SimpleRabbitListenerContainerFactory containerFactory = new SimpleRabbitListenerContainerFactory();
        containerFactory.setConnectionFactory(connectionFactory);
        containerFactory.setMessageConverter(messageConverter);

        return containerFactory;
    }

}
