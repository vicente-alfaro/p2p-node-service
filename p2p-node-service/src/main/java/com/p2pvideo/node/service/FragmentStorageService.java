package com.p2pvideo.node.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.p2pvideo.node.config.NodeProperties;

@Service
public class FragmentStorageService {

    private final NodeProperties nodeProperties;

    public FragmentStorageService(NodeProperties nodeProperties) {
        this.nodeProperties = nodeProperties;
    }

    public List<String> listFragments() throws IOException {
        Path fragmentsDirectory = Path.of(nodeProperties.getFragmentsPath());

        if (!Files.exists(fragmentsDirectory)) {
            Files.createDirectories(fragmentsDirectory);
        }

        return Files.list(fragmentsDirectory)
                .filter(Files::isRegularFile)
                .map(path -> path.getFileName().toString())
                .sorted()
                .toList();
    }

    public Resource getFragmentAsResource(String fragmentName) {
        Path fragmentPath = Path.of(nodeProperties.getFragmentsPath(), fragmentName);

        if (!Files.exists(fragmentPath) || !Files.isRegularFile(fragmentPath)) {
            throw new IllegalArgumentException("Fragment not found: " + fragmentName);
        }

        return new FileSystemResource(fragmentPath);
    }

    public Path getFragmentPath(String fragmentName) {
        return Path.of(nodeProperties.getFragmentsPath(), fragmentName);
    }
}