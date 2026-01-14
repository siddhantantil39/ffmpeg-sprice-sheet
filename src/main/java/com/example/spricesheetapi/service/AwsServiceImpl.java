package com.example.spricesheetapi.service;

import com.example.spricesheetapi.contract.AwsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.UploadPartRequest;
import software.amazon.awssdk.services.s3.model.UploadPartResponse;

import java.io.InputStream;
import java.util.List;

@Service
public class AwsServiceImpl implements AwsService {

    @Autowired
    private final S3Client s3Client;

    public AwsServiceImpl(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @Override
    public void init() {

    }

    @Override
    public UploadPartResponse store(String bucketName, String keyName, Long contentLength, String contentType, InputStream inputStream, String uploadId) {
        if (contentLength == null || contentLength <= 0) {
            throw new IllegalArgumentException("Content length must be provided");
        }

        UploadPartRequest request = UploadPartRequest.builder()
                .bucket(bucketName)
                .key(keyName)
                .uploadId(uploadId)
                .contentLength(contentLength)
                .build();

        UploadPartResponse response = s3Client.uploadPart(
                request,
                RequestBody.fromInputStream(inputStream, contentLength)
        );

        return response;
    }

    @Override
    public List<String> listFiles(String bucketName) {
        return List.of();
    }
}
