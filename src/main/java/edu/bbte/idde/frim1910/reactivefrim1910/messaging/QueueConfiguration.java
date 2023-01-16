package edu.bbte.idde.frim1910.reactivefrim1910.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfiguration {

    @Value("${QUEUE:reactive_queue}")
    private String queue;

    @Value("${EXCHANGE:reactive_q}")
    private String exchange;

    @Bean
    public QueueConfig getQueueConfig() {
        return new QueueConfig(
            queue,
            exchange
        );
    }

    @Bean
    public Queue reactiveQueue() {
        return new Queue(queue, true);
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    public Binding reactiveQueueBinding(Queue reactiveQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(reactiveQueue).to(directExchange).with(queue);
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter(new ObjectMapper());
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
