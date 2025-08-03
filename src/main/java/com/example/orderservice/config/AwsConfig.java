package com.example.orderservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.regions.Region;

import java.net.URI;

@Configuration
public class AwsConfig {

    private String getEnv(String key) {
        String value = System.getenv(key);
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("Missing required environment variable: " + key);
        }
        return value;
    }

    private StaticCredentialsProvider credentialsProvider() {
        return StaticCredentialsProvider.create(
            AwsBasicCredentials.create(
                getEnv("AWS_ACCESS_KEY"),
                getEnv("AWS_SECRET_KEY")
            )
        );
    }

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .region(Region.of(getEnv("AWS_REGION")))
                .endpointOverride(URI.create("https://s3.ap-south-1.amazonaws.com"))
                .credentialsProvider(credentialsProvider())
                .build();
    }

    @Bean
    public SnsClient snsClient() {
        return SnsClient.builder()
                .region(Region.of(getEnv("AWS_REGION")))
                .credentialsProvider(credentialsProvider())
                .build();
    }

    @Bean
    public DynamoDbClient dynamoDbClient() {
        return DynamoDbClient.builder()
                .region(Region.of(getEnv("AWS_REGION")))
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
