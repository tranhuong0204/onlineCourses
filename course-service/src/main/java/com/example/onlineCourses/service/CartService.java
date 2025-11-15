package com.example.onlineCourses.service;

import com.example.onlineCourses.model.CartItem;
import com.example.onlineCourses.model.Course;
import com.example.onlineCourses.model.Order;
import com.example.onlineCourses.model.User;
import com.example.onlineCourses.repository.CartItemRepository;
import com.example.onlineCourses.repository.CourseRepository;
import com.example.onlineCourses.repository.OrderRepository;
import com.example.onlineCourses.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    public void addCourseToCart(Long userId, Long courseId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        Optional<CartItem> existingItem = cartItemRepository.findByUserAndCourse(user, course);

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + 1);
            cartItemRepository.save(item);
        } else {
            CartItem newItem = new CartItem(user, course);
            cartItemRepository.save(newItem);
        }
    }

    public List<CartItem> getCartItems(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return cartItemRepository.findByUser(user);
    }

    public void removeCourseFromCart(Long userId, Long courseId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        cartItemRepository.findByUserAndCourse(user, course)
                .ifPresent(cartItemRepository::delete);
    }

//    public Long getCartTotal(Long userId) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        List<CartItem> cartItems = cartItemRepository.findByUser(user);
//
//        return cartItems.stream()
//                .mapToLong(CartItem::getPrice)
//                .sum();
//    }

    public Order checkout(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<CartItem> cartItems = cartItemRepository.findByUser(user);

        long totalAmount = cartItems.stream()
                .mapToLong(CartItem::getPrice)
                .sum();

        Order order = new Order();
        order.setUserId(user.getId());
        order.setAmount(totalAmount);
        order.setStatus("PENDING");
        orderRepository.save(order);

        // clear cart sau khi tạo đơn hàng
        cartItemRepository.deleteAll(cartItems);

        return order;
    }


    public void clearCart(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<CartItem> cartItems = cartItemRepository.findByUser(user);
        cartItemRepository.deleteAll(cartItems);
    }

}

