package com.eventure.profile_service.service.serviceImpl;

import com.eventure.profile_service.exception.ImageUploadException;
import com.eventure.profile_service.service.FileStorageService;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileStorageServiceImpl implements FileStorageService {

    private final S3AsyncClient s3AsyncClient;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Override
    public String uploadImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File must not be empty");
        }
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("Invalid file type. Only images allowed");
        }

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

        try {
            PutObjectRequest putOb =
                    PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(fileName)
                            .contentType(contentType)
                            .build();

            CompletableFuture<Void> future =
                    s3AsyncClient
                            .putObject(putOb, AsyncRequestBody.fromBytes(file.getBytes()))
                            .thenAccept(
                                    response ->
                                            log.info("File {} uploaded successfully", fileName));

            future.join();

            return "https://" + bucketName + ".s3.eu-north-1.amazonaws.com/" + fileName;

        } catch (Exception e) {
            log.error("S3 Upload Error", e);
            throw new ImageUploadException("Failed to upload file to S3", e);
        }
    }
}
