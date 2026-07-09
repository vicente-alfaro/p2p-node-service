package com.p2pvideo.node.service;

import com.p2pvideo.node.config.RabbitMQConfig;
import com.p2pvideo.node.dto.FragmentAvailableEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class FragmentEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public FragmentEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishFragmentAvailable(FragmentAvailableEvent event) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.FRAGMENT_EXCHANGE,
                "",
                event
        );

        System.out.println("Published fragment event:");
        System.out.println("Node: " + event.getNodeId());
        System.out.println("Fragment: " + event.getFragmentName());
    }
}