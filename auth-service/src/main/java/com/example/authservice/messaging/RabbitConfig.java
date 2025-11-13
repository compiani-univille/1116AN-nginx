package com.example.authservice.messaging;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
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
    TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    Queue notificationQueue() {
        return QueueBuilder.durable(NOTIFICATION_QUEUE_NAME).build();
    }

    @Bean
    Binding notificationQueueBinding() {
        return BindingBuilder
            .bind(notificationQueue())
            .to(exchange())
            .with(USER_CREATED_ROUTING_KEY);
    }

    @Bean
    Queue cupomQueue() {
        return QueueBuilder.durable(CUPOM_QUEUE_NAME).build();
    }

    @Bean
    Binding cupomQueueBinding() {
        return BindingBuilder
            .bind(cupomQueue())
            .to(exchange())
            .with(USER_CREATED_ROUTING_KEY);
    }

    @Bean
    RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    ApplicationListener<ApplicationReadyEvent> initializeRabbitMQ(RabbitAdmin rabbitAdmin) {
        return event -> {
            rabbitAdmin.initialize();
        };
    }
}
