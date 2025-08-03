package com.example.orderservice.model;

import java.time.Instant;
import java.util.UUID;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;

@DynamoDbBean
public class Order {

    private UUID id;
    private String productName;
    private Double price;
    private Instant orderDate;
    private String status;
    private String invoiceUrl;

    public Order() {}

    public Order(UUID id, String productName, Double price, Instant orderDate, String status, String invoiceUrl) {
        this.id = id;
        this.productName = productName;
        this.price = price;
        this.orderDate = orderDate;
        this.status = status;
        this.invoiceUrl = invoiceUrl;
    }

    @DynamoDbPartitionKey
    @DynamoDbAttribute("id")
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @DynamoDbAttribute("productName")
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @DynamoDbAttribute("price")
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @DynamoDbAttribute("orderDate")
    public Instant getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Instant orderDate) {
        this.orderDate = orderDate;
    }

    @DynamoDbAttribute("status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @DynamoDbAttribute("invoiceUrl")
    public String getInvoiceUrl() {
        return invoiceUrl;
    }

    public void setInvoiceUrl(String invoiceUrl) {
        this.invoiceUrl = invoiceUrl;
    }
}
