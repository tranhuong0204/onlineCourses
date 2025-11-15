package com.example.onlineCourses.service;

import com.example.onlineCourses.model.CartItem;
import com.example.onlineCourses.model.Order;
import com.example.onlineCourses.model.User;
import com.example.onlineCourses.repository.CartItemRepository;
import com.example.onlineCourses.repository.OrderRepository;
import com.example.onlineCourses.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    private UserRepository userRepo;
    private CartItemRepository cartItemRepo;

    public Order checkout(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<CartItem> cartItems = cartItemRepo.findByUser(user);
        long totalAmount = cartItems.stream()
                .mapToLong(CartItem::getPrice)
                .sum();

        Order order = new Order();
        order.setUserId(user.getId());
        order.setAmount(totalAmount);
        order.setStatus("PENDING");
        orderRepository.save(order);

        // clear cart sau khi tạo đơn hàng
        cartItemRepo.deleteAll(cartItems);

        return order;
    }

}
