package com.example.spricesheetapi.contract;

import org.springframework.core.io.Resource;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {
    void init();

    void store(InputStream stream, int partNumber, String uploadId);
    Stream<Path> loadAll();
    Path load(String filename);
    void deleteAll();
    Resource loadAsResource(String filename);

}
