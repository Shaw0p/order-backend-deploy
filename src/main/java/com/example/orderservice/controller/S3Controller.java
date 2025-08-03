// File: src/main/java/com/example/orderservice/controller/S3Controller.java
package com.example.orderservice.controller;

import com.example.orderservice.model.Order;
import com.example.orderservice.service.OrderService;
import com.example.orderservice.service.S3Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class S3Controller {

    private final S3Service s3Service;
    private final OrderService orderService;

    public S3Controller(S3Service s3Service, OrderService orderService) {
        this.s3Service = s3Service;
        this.orderService = orderService;
    }

    @PostMapping("/{id}/upload-invoice")
    public ResponseEntity<String> uploadInvoice(@PathVariable("id") UUID id,
                                                @RequestParam("file") MultipartFile file) {
        try {
            String url = s3Service.uploadFile(file);

            Order order = orderService.getOrder(id);
            order.setInvoiceUrl(url);
            orderService.updateOrder(id, order);

            return ResponseEntity.ok("Invoice uploaded and linked to order. URL: " + url);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Upload failed: " + e.getMessage());
        }
    }
}
