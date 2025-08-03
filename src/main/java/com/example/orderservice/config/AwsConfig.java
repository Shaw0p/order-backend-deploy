package com.example.orderservice.config;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.sns.SnsClient;

import java.net.URI;

@Configuration
@ConfigurationProperties(prefix = "aws")
@Data
public class AwsConfig {

    private String region;
    private Credentials credentials;

    @Data
    public static class Credentials {
        private String accessKey;
        private String secretKey;
    }

    @PostConstruct
    public void logAwsCredentials() {
        System.out.println("✅ AWS Region: " + region);
        if (credentials == null) {
            System.out.println("❌ Credentials are NULL");
        } else {
            System.out.println("✅ AWS Access Key: " + credentials.getAccessKey());
            System.out.println("✅ AWS Secret Key: " + (credentials.getSecretKey() != null ? "Provided" : "NULL"));
        }
    }

    private StaticCredentialsProvider credentialsProvider() {
        return StaticCredentialsProvider.create(
                AwsBasicCredentials.create(
                        credentials.getAccessKey(),
                        credentials.getSecretKey()
                )
        );
    }

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .region(Region.of(region))
                .endpointOverride(URI.create("https://s3.ap-south-1.amazonaws.com"))
                .credentialsProvider(credentialsProvider())
                .build();
    }

    @Bean
    public SnsClient snsClient() {
        return SnsClient.builder()
                .region(Region.of(region))
                .credentialsProvider(credentialsProvider())
                .build();
    }

    @Bean
    public DynamoDbClient dynamoDbClient() {
        return DynamoDbClient.builder()
                .region(Region.of(region))
                .credentialsProvider(credentialsProvider())
                .build();
    }

    @Bean
    public DynamoDbEnhancedClient enhancedClient(DynamoDbClient dynamoDbClient) {
        return DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();
    }
}
