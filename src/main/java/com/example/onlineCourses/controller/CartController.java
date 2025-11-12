package com.example.onlineCourses.controller;

import com.example.onlineCourses.model.CartItem;
import com.example.onlineCourses.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "http://localhost:3000")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add-to-cart")
    public ResponseEntity<String> addCourseToCart(@RequestParam Long userId, @RequestParam Long courseId) {
        cartService.addCourseToCart(userId, courseId);
        return ResponseEntity.ok("Course added to cart");
    }

    @GetMapping
    public ResponseEntity<List<CartItem>> getCart(@RequestParam Long userId) {
        return ResponseEntity.ok(cartService.getCartItems(userId));
    }

    @DeleteMapping("/remove-from-cart")
    public ResponseEntity<String> removeCourseFromCart(@RequestParam Long userId, @RequestParam Long courseId) {
        cartService.removeCourseFromCart(userId, courseId);
        return ResponseEntity.ok("Course removed from cart");
    }


}

