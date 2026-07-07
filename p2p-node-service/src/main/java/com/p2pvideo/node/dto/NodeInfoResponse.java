package com.p2pvideo.node.dto;

public class NodeInfoResponse {

    private String nodeId;
    private String fragmentsPath;
    private String outputPath;

    public NodeInfoResponse() {
    }

    public NodeInfoResponse(String nodeId, String fragmentsPath, String outputPath) {
        this.nodeId = nodeId;
        this.fragmentsPath = fragmentsPath;
        this.outputPath = outputPath;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
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
}