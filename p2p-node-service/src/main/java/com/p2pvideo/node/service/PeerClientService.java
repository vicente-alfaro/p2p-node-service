package com.p2pvideo.node.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.p2pvideo.node.config.NodeProperties;

@Service
public class PeerClientService {

    private final NodeProperties nodeProperties;
    private final RestTemplate restTemplate;

    public PeerClientService(NodeProperties nodeProperties) {
        this.nodeProperties = nodeProperties;
        this.restTemplate = new RestTemplate();
    }

    public byte[] downloadFragmentFromPeer(String fromNodeId, String fragmentName) {
        String peerBaseUrl = nodeProperties.getPeers().get(fromNodeId);

        if (peerBaseUrl == null) {
            throw new IllegalArgumentException("Unknown peer: " + fromNodeId);
        }

        String url = peerBaseUrl + "/api/fragments/" + fragmentName + "/download";

        System.out.println("Downloading fragment from peer:");
        System.out.println("From node: " + fromNodeId);
        System.out.println("URL: " + url);

        return restTemplate.getForObject(url, byte[].class);
    }
}