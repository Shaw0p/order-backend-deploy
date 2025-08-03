package com.example.orderservice.repository;

import com.example.orderservice.model.Order;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class OrderRepository {

    private final DynamoDbTable<Order> orderTable;

    public OrderRepository(DynamoDbEnhancedClient enhancedClient) {
        this.orderTable = enhancedClient.table("orders", TableSchema.fromBean(Order.class));
    }

    public void saveOrder(Order order) {
        orderTable.putItem(order);
    }

    public Order getOrderById(UUID id) {
        return orderTable.getItem(r -> r.key(k -> k.partitionValue(id.toString())));
    }

    public List<Order> getAllOrders() {
        return orderTable.scan()
                .items()
                .stream()
                .collect(Collectors.toList());
    }

    public void deleteOrder(UUID id) {
        orderTable.deleteItem(r -> r.key(k -> k.partitionValue(id.toString())));
    }
}
