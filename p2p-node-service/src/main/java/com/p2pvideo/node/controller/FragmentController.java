package com.p2pvideo.node.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.p2pvideo.node.config.NodeProperties;
import com.p2pvideo.node.dto.NodeInfoResponse;
import com.p2pvideo.node.service.FragmentStorageService;

@RestController
@RequestMapping("/api")
public class FragmentController {

    private final NodeProperties nodeProperties;
    private final FragmentStorageService fragmentStorageService;

    public FragmentController(
            NodeProperties nodeProperties,
            FragmentStorageService fragmentStorageService
    ) {
        this.nodeProperties = nodeProperties;
        this.fragmentStorageService = fragmentStorageService;
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
}