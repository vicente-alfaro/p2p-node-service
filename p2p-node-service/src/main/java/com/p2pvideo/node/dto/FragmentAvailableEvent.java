package com.p2pvideo.node.dto;

public class FragmentAvailableEvent {

    private String nodeId;
    private String fragmentName;
    private String message;

    public FragmentAvailableEvent() {
    }

    public FragmentAvailableEvent(String nodeId, String fragmentName, String message) {
        this.nodeId = nodeId;
        this.fragmentName = fragmentName;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
