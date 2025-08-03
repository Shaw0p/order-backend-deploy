package com.example.orderservice.controller;

import com.example.orderservice.model.Order;
import com.example.orderservice.service.OrderService;
import com.example.orderservice.service.S3Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final S3Service s3Service;

    public OrderController(OrderService orderService, S3Service s3Service) {
        this.orderService = orderService;
        this.s3Service = s3Service;
    }

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable UUID id) {
        return orderService.getOrder(id);
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @PutMapping("/{id}")
    public Order updateOrder(@PathVariable UUID id, @RequestBody Order updatedOrder) {
        return orderService.updateOrder(id, updatedOrder);
    }

    @DeleteMapping("/{id}")
    public String deleteOrder(@PathVariable UUID id) {
        return orderService.deleteOrder(id);
    }

    @PostMapping("/{id}/upload-invoice")
    public ResponseEntity<String> uploadInvoice(@PathVariable UUID id, @RequestParam("file") MultipartFile file) {
        try {
            String invoiceUrl = s3Service.uploadFile(file);
            Order order = orderService.getOrder(id);
            if (order == null) {
                return ResponseEntity.notFound().build();
            }

            order.setInvoiceUrl(invoiceUrl);
            orderService.updateOrder(id, order);

            return ResponseEntity.ok("Invoice uploaded successfully: " + invoiceUrl);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Upload failed: " + e.getMessage());
        }
    }
}
