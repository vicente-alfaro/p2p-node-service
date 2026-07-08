package com.p2pvideo.node.dto;

public class DownloadRequest {

    private String fragmentName;
    private String fromNodeId;

    public DownloadRequest() {
    }

    public DownloadRequest(String fragmentName, String fromNodeId) {
        this.fragmentName = fragmentName;
        this.fromNodeId = fromNodeId;
    }

    public String getFragmentName() {
        return fragmentName;
    }

    public void setFragmentName(String fragmentName) {
        this.fragmentName = fragmentName;
    }

    public String getFromNodeId() {
        return fromNodeId;
    }

    public void setFromNodeId(String fromNodeId) {
        this.fromNodeId = fromNodeId;
    }
}