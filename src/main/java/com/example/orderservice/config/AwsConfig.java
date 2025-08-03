package com.example.orderservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.sns.SnsClient;

import java.net.URI;

@Configuration
public class AwsConfig {

    private String getEnv(String key, boolean required) {
        String value = System.getenv(key);
        if (required && (value == null || value.isEmpty())) {
            throw new IllegalArgumentException("Missing required environment variable: " + key);
        }
        return value;
    }

    private StaticCredentialsProvider credentialsProvider() {
        String accessKey = getEnv("AWS_ACCESS_KEY", true);
        String secretKey = getEnv("AWS_SECRET_KEY", true);
        return StaticCredentialsProvider.create(
                AwsBasicCredentials.create(accessKey, secretKey)
        );
    }

    private Region getAwsRegion() {
        return Region.of(getEnv("AWS_REGION", true));
    }

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .region(getAwsRegion())
                .endpointOverride(URI.create("https://s3.ap-south-1.amazonaws.com"))
                .credentialsProvider(credentialsProvider())
                .build();
    }

    @Bean
    public SnsClient snsClient() {
        return SnsClient.builder()
                .region(getAwsRegion())
                .credentialsProvider(credentialsProvider())
                .build();
    }

    @Bean
    public DynamoDbClient dynamoDbClient() {
        return DynamoDbClient.builder()
                .region(getAwsRegion())
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
