// File: src/main/java/com/example/orderservice/service/S3Service.java
package com.example.orderservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.core.sync.RequestBody;

import java.io.IOException;
import java.util.UUID;

@Service
public class S3Service {

    private final S3Client s3Client;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    public S3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public String uploadFile(MultipartFile file) throws IOException {
    String fileName = "invoices/" + UUID.randomUUID() + "_" + file.getOriginalFilename();

    PutObjectRequest putObjectRequest = PutObjectRequest.builder()
        .bucket(bucketName)
        .key(fileName)
        .contentType(file.getContentType())
        
        .build();

    s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));

    return "https://" + bucketName + ".s3.ap-south-1.amazonaws.com/" + fileName;
}


    
}
