package com.example.spricesheetapi.controller;

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

    @Autowired
    public UploadController(StorageService storageService){
        this.storageService = storageService;
    };

    @PostMapping(consumes = "application/octet-stream")
    public ResponseEntity<String> upload(
        @RequestParam("uploadId")  String uploadId,
        @RequestParam("partNumber") int partNumber,
        HttpServletRequest request
    ) throws IOException{
        long contentLength = request.getContentLengthLong();
        if (contentLength <= 0) {
            return ResponseEntity.badRequest().body("Missing Content-Length");
        }

        try(InputStream inputStream = request.getInputStream()){
            storageService.store(inputStream, partNumber, uploadId);
        }

        return ResponseEntity.ok("File uploaded");
    }

}
