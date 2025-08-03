package com.example.orderservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;

@Service
public class SnsNotificationService {

    private final SnsClient snsClient;

    @Value("${aws.sns.topic.arn}")
    private String topicArn;

    public SnsNotificationService(SnsClient snsClient) {
        this.snsClient = snsClient;
    }

    public void publishOrderNotification(String message) {
        PublishRequest request = PublishRequest.builder()
                .message(message)
                .topicArn(topicArn)
                .build();
        snsClient.publish(request);
    }
}
