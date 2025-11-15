package com.example.onlineCourses.controller;

import com.example.onlineCourses.model.Order;
import com.example.onlineCourses.repository.OrderRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderRepository orderRepo;

    public OrderController(OrderRepository orderRepo) {
        this.orderRepo = orderRepo;
    }

    // Tạo đơn hàng mới
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        order.setStatus("PENDING");
        orderRepo.save(order);
        return ResponseEntity.ok(order);
    }

    // Lấy đơn hàng theo ID
    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrder(@PathVariable String orderId) {
        return orderRepo.findByOrderId(orderId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

