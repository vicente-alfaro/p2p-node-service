package com.p2pvideo.node.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String FRAGMENT_EXCHANGE = "fragment.exchange";

    private final NodeProperties nodeProperties;

    public RabbitMQConfig(NodeProperties nodeProperties) {
        this.nodeProperties = nodeProperties;
    }

    @Bean
    public FanoutExchange fragmentExchange() {
        return new FanoutExchange(FRAGMENT_EXCHANGE);
    }

    @Bean
    public Queue fragmentQueue() {
        return new Queue("fragment.queue." + nodeProperties.getId(), true);
    }

    @Bean
    public Binding fragmentBinding(Queue fragmentQueue, FanoutExchange fragmentExchange) {
        return BindingBuilder
                .bind(fragmentQueue)
                .to(fragmentExchange);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }
}