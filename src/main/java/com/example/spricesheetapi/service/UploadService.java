package com.example.spricesheetapi.service;

import com.example.spricesheetapi.contract.StorageService;
import com.example.spricesheetapi.exception.StorageException;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Service
public class UploadService implements StorageService {
    @Override
    public void init() {

    }

    @Override
    public void store(InputStream inputStream, int partNumber, String uploadId) {
        try{
            if(inputStream == null) {
                throw new StorageException("inputStream is null");
            }

            Path BASE_DIR = Path.of("/tmp/uploads", uploadId);
            Files.createDirectories(BASE_DIR);

            Path path = BASE_DIR.resolve("part_" + partNumber);
            Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
        }
        catch (Exception e){
            throw new StorageException(e.getMessage());
        }
    }

    @Override
    public Stream<Path> loadAll() {
        return Stream.empty();
    }

    @Override
    public Path load(String filename) {
        return null;
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public Resource loadAsResource(String filename) {
        return null;
    }
}
