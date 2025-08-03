package com.example.orderservice.service;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.*;

@Service
public class NotificationService {

    private final SnsClient snsClient;

    public NotificationService(SnsClient snsClient) {
        this.snsClient = snsClient;
    }

    public void sendNotification(String message, String topicArn) {
        PublishRequest request = PublishRequest.builder()
                .message(message)
                .topicArn(topicArn)
                .build();

        snsClient.publish(request);
    }
}
