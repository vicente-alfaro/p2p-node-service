package com.p2pvideo.node.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.p2pvideo.node.config.NodeProperties;
import com.p2pvideo.node.dto.DownloadRequest;
import com.p2pvideo.node.dto.DownloadResponse;
import com.p2pvideo.node.dto.FragmentAvailableEvent;
import com.p2pvideo.node.dto.NodeInfoResponse;
import com.p2pvideo.node.service.FragmentEventPublisher;
import com.p2pvideo.node.service.FragmentStorageService;
import com.p2pvideo.node.service.PeerClientService;

@RestController
@RequestMapping("/api")
public class FragmentController {

    private final NodeProperties nodeProperties;
    private final FragmentStorageService fragmentStorageService;
    private final PeerClientService peerClientService;
    private final FragmentEventPublisher fragmentEventPublisher;

    public FragmentController(
            NodeProperties nodeProperties,
            FragmentStorageService fragmentStorageService,
            PeerClientService peerClientService,
            FragmentEventPublisher fragmentEventPublisher
    ) {
        this.nodeProperties = nodeProperties;
        this.fragmentStorageService = fragmentStorageService;
        this.peerClientService = peerClientService;
        this.fragmentEventPublisher = fragmentEventPublisher;
    }

    @GetMapping("/node")
    public NodeInfoResponse getNodeInfo() {
        return new NodeInfoResponse(
                nodeProperties.getId(),
                nodeProperties.getFragmentsPath(),
                nodeProperties.getOutputPath()
        );
    }

    @GetMapping("/fragments")
    public ResponseEntity<List<String>> listFragments() throws IOException {
        List<String> fragments = fragmentStorageService.listFragments();
        return ResponseEntity.ok(fragments);
    }

    @GetMapping("/fragments/{fragmentName}/download")
    public ResponseEntity<Resource> downloadFragment(@PathVariable String fragmentName) {
        Resource resource = fragmentStorageService.getFragmentAsResource(fragmentName);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + fragmentName + "\""
                )
                .body(resource);
    }

    @PostMapping("/download")
    public ResponseEntity<DownloadResponse> downloadFromPeer(@RequestBody DownloadRequest request) {
        try {
            byte[] fragmentBytes = peerClientService.downloadFragmentFromPeer(
                    request.getFromNodeId(),
                    request.getFragmentName()
            );

            fragmentStorageService.saveFragment(
                    request.getFragmentName(),
                    fragmentBytes
            );

            FragmentAvailableEvent event = new FragmentAvailableEvent(
                    nodeProperties.getId(),
                    request.getFragmentName(),
                    "Fragment available"
            );

            fragmentEventPublisher.publishFragmentAvailable(event);

            DownloadResponse response = new DownloadResponse(
                    nodeProperties.getId(),
                    request.getFragmentName(),
                    request.getFromNodeId(),
                    "DOWNLOADED",
                    "Fragment downloaded successfully"
            );

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            DownloadResponse response = new DownloadResponse(
                    nodeProperties.getId(),
                    request.getFragmentName(),
                    request.getFromNodeId(),
                    "ERROR",
                    e.getMessage()
            );

            return ResponseEntity.internalServerError().body(response);
        }
    }
}