package com.example.spricesheetapi.controller;

import com.example.spricesheetapi.contract.AwsService;
import com.example.spricesheetapi.contract.StorageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/api/uploads")
public class UploadController {

    private final StorageService storageService;
    private final AwsService awsService;

    @Autowired
    public UploadController(StorageService storageService,  AwsService awsService) {
        this.storageService = storageService;
        this.awsService = awsService;
    };

    @PostMapping(consumes = "application/octet-stream")
    public ResponseEntity<String> upload(
        @RequestParam("uploadId")  String uploadId,
        @RequestParam("partNumber") int partNumber,
        @RequestParam("objectKey") String objectKey,
        HttpServletRequest request
    ) throws IOException{
        long contentLength = request.getContentLengthLong();
        String contentType = request.getContentType();
        if (contentLength <= 0) {
            return ResponseEntity.badRequest().body("Missing Content-Length");
        }

        InputStream inputStream = request.getInputStream();
            awsService.store(
                    contentLength,
                    contentType,
                    inputStream,
                    uploadId,
                    partNumber,
                    objectKey
            );
        return ResponseEntity.ok("Part uploaded");
    }

}
