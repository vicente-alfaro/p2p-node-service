package com.p2pvideo.node.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "node")
public class NodeProperties {

    private String id;
    private String fragmentsPath;
    private String outputPath;
    private Map<String, String> peers = new HashMap<>();

    public NodeProperties() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFragmentsPath() {
        return fragmentsPath;
    }

    public void setFragmentsPath(String fragmentsPath) {
        this.fragmentsPath = fragmentsPath;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    public Map<String, String> getPeers() {
        return peers;
    }

    public void setPeers(Map<String, String> peers) {
        this.peers = peers;
    }
}