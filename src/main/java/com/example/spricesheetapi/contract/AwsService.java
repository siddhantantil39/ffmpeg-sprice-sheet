package com.example.spricesheetapi.contract;

import software.amazon.awssdk.services.s3.model.UploadPartResponse;

import java.io.InputStream;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface AwsService {
    void init();

    CompletableFuture<Void> store(
            Long contentLength,
            String contentType,
            InputStream value,
            String uploadId,
            int partNumber,
            String objectKey);

    List<String> listFiles(String bucketName);
}
