package com.example.spricesheetapi.contract;

import software.amazon.awssdk.services.s3.model.UploadPartResponse;

import java.io.InputStream;
import java.util.List;

public interface AwsService {
    void init();

    UploadPartResponse store(
            String bucketName,
            String keyName,
            Long contentLength,
            String contentType,
            InputStream value,
            String uploadId);

    List<String> listFiles(String bucketName);
}
