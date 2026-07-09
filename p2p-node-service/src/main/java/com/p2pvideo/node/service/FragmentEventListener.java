package com.p2pvideo.node.service;

import com.p2pvideo.node.config.NodeProperties;
import com.p2pvideo.node.dto.FragmentAvailableEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class FragmentEventListener {

    private final NodeProperties nodeProperties;

    public FragmentEventListener(NodeProperties nodeProperties) {
        this.nodeProperties = nodeProperties;
    }

    @RabbitListener(queues = "#{fragmentQueue.name}")
    public void listen(FragmentAvailableEvent event) {
        if (event.getNodeId().equals(nodeProperties.getId())) {
            return;
        }

        System.out.println("Fragment availability event received:");
        System.out.println("Current node: " + nodeProperties.getId());
        System.out.println("Peer node: " + event.getNodeId());
        System.out.println("Available fragment: " + event.getFragmentName());
        System.out.println("Message: " + event.getMessage());
    }
}