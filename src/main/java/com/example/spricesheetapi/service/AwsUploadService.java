package com.example.spricesheetapi.service;

import com.example.spricesheetapi.contract.AwsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.UploadPartRequest;
import software.amazon.awssdk.services.s3.model.UploadPartResponse;

import java.io.InputStream;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class AwsUploadService implements AwsService {

    private final S3Client s3Client;

    private final String bucketName;


    public AwsUploadService(S3Client s3Client, @Value("${cloud.aws.s3.bucket}")String bucketName) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
    }

    @Override
    public void init() {

    }

    @Async("uploadTaskExecutor")
    @Override
    public CompletableFuture<Void> store(
            Long contentLength,
            String contentType,
            InputStream inputStream,
            String uploadId,
            int partNumber,
            String objectKey) {
        if(contentLength == null || contentLength <= 0) {
            throw new IllegalArgumentException("Content length must be provided");
        }

        if(partNumber <= 0) {
            throw new IllegalArgumentException("partNumber must be >= 1");
        }

        UploadPartRequest request = UploadPartRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .uploadId(uploadId)
                .contentLength(contentLength)
                .partNumber(partNumber)
                .build();

        try(InputStream is = inputStream){
            s3Client.uploadPart(
                    request,
                    RequestBody.fromInputStream(is, contentLength));
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

         return CompletableFuture.completedFuture(null);
    }

    //s3 trigger with a POST API to process FFmpeg frames
    @Override
    public List<String> listFiles(String bucketName) {
        return List.of();
    }
}
