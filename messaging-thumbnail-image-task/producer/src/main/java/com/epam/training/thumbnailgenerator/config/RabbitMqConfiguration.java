package com.epam.training.thumbnailgenerator.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfiguration {
    @Bean
    public DirectExchange directExchange(MessagingProperties messagingProperties) {
        return new DirectExchange(messagingProperties.getExchangeName());
    }

    @Bean
    public Queue queue(MessagingProperties messagingProperties) {
        return new Queue(messagingProperties.getQueueName(), true);
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange directExchange, MessagingProperties messagingProperties) {
        return BindingBuilder.bind(queue).to(directExchange).with(messagingProperties.getRoutingKey());
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return new Jackson2JsonMessageConverter(objectMapper);
    }
}
