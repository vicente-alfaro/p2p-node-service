package com.p2pvideo.node.dto;

public class DownloadResponse {

    private String nodeId;
    private String fragmentName;
    private String downloadedFrom;
    private String status;
    private String message;

    public DownloadResponse() {
    }

    public DownloadResponse(String nodeId, String fragmentName, String downloadedFrom, String status, String message) {
        this.nodeId = nodeId;
        this.fragmentName = fragmentName;
        this.downloadedFrom = downloadedFrom;
        this.status = status;
        this.message = message;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getFragmentName() {
        return fragmentName;
    }

    public void setFragmentName(String fragmentName) {
        this.fragmentName = fragmentName;
    }

    public String getDownloadedFrom() {
        return downloadedFrom;
    }

    public void setDownloadedFrom(String downloadedFrom) {
        this.downloadedFrom = downloadedFrom;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}