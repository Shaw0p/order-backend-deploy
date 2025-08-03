package com.example.orderservice.service;

import com.example.orderservice.model.Order;
import com.example.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository repository;
    private final NotificationService notificationService;

    public OrderService(OrderRepository repository, NotificationService notificationService) {
        this.repository = repository;
        this.notificationService = notificationService;
    }

    public Order createOrder(Order order) {
        UUID id = UUID.randomUUID();
        order.setId(id);
        order.setOrderDate(Instant.now());
        order.setStatus("PENDING");
        repository.saveOrder(order);

        // ✅ Corrected ARN
        String topicArn = "arn:aws:sns:ap-south-1:851725255728:order-notify-topic";
        String message = "New Order Placed: " + order.getProductName() + " with ID " + id;
        notificationService.sendNotification(message, topicArn);

        return order;
    }

    public Order getOrder(UUID id) {
        return repository.getOrderById(id);
    }

    public List<Order> getAllOrders() {
        return repository.getAllOrders();
    }

    public Order updateOrder(UUID id, Order updatedOrder) {
        Order existing = repository.getOrderById(id);
        if (existing != null) {
            updatedOrder.setId(id);
            updatedOrder.setOrderDate(existing.getOrderDate());
            repository.saveOrder(updatedOrder);
            return updatedOrder;
        }
        return null;
    }

    public String deleteOrder(UUID id) {
        repository.deleteOrder(id);

        // ✅ Corrected ARN for deletion message
        String topicArn = "arn:aws:sns:ap-south-1:851725255728:order-notify-topic";
        String message = "Order deleted with ID: " + id;
        notificationService.sendNotification(message, topicArn);

        return "Order deleted: " + id;
    }
}
