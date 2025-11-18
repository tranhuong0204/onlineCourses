package com.example.onlineCourses.controller;

import com.example.onlineCourses.DTO.OrderStatusUpdateRequest;
import com.example.onlineCourses.model.CartItem;
import com.example.onlineCourses.model.Order;
import com.example.onlineCourses.repository.OrderRepository;
import com.example.onlineCourses.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:3000") // cho phép React gọi
public class OrderController {

    private final OrderRepository orderRepo;
    private final CartService cartService;

    public OrderController(OrderRepository orderRepo, CartService cartService) {
        this.orderRepo = orderRepo;
        this.cartService = cartService;
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }
    // Tạo đơn hàng từ giỏ hàng
//    @PostMapping("/checkout")
//    public ResponseEntity<Order> checkout(@RequestBody List<CartItem> cartItems,
//                                          @RequestParam Long userId) {
//        long totalAmount = cartItems.stream()
//                .mapToLong(item -> item.getPrice() * item.getQuantity())
//                .sum();
//
//        Order order = new Order();
//        order.setUserId(userId);
//        order.setAmount(totalAmount);
//        order.setStatus("PENDING");
//        orderRepo.save(order);
//
//        return ResponseEntity.ok(order);
//    }
    @PostMapping("/checkout")
    public ResponseEntity<Order> checkout(@RequestParam Long userId) {
        Order order = cartService.checkout(userId);
        return ResponseEntity.ok(order);
    }


    // Lấy đơn hàng theo ID
    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrder(@PathVariable String orderId) {
        return orderRepo.findByOrderId(orderId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        return orderRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


//    // IPN callback từ VNPay
//    @PostMapping("/vnpay/ipn")
//    public ResponseEntity<String> handleVnpayIpn(@RequestParam Map<String, String> params) {
//        // Lấy các tham số từ VNPay
//        String txnRef = params.get("vnp_TxnRef");          // Mã đơn hàng
//        String responseCode = params.get("vnp_ResponseCode"); // Mã phản hồi
//        String transactionStatus = params.get("vnp_TransactionStatus"); // Trạng thái giao dịch
//        String secureHash = params.get("vnp_SecureHash"); // Chữ ký
//
//        // TODO: Kiểm tra chữ ký secureHash với secretKey của bạn
//        // Nếu chữ ký không hợp lệ thì return ResponseEntity.badRequest().body("Invalid signature");
//
//        // Tìm đơn hàng theo orderId
//        Order order = orderRepo.findByOrderId(txnRef)
//                .orElse(null);
//
//        if (order == null) {
//            return ResponseEntity.badRequest().body("Order not found");
//        }
//
//        // Cập nhật trạng thái đơn hàng
//        if ("00".equals(responseCode) && "00".equals(transactionStatus)) {
//            order.setStatus("PAID");
//        } else {
//            order.setStatus("FAILED");
//        }
//
//        orderRepo.save(order);
//
//        // Trả về cho VNPay biết đã nhận IPN
//        return ResponseEntity.ok("IPN received");
//    }
//
//    @PostMapping("/update-status")
//    public ResponseEntity<String> updateOrderStatus(@RequestBody OrderStatusUpdateRequest req) {
//        Order order = orderRepo.findByOrderId(req.getOrderId())
//                .orElse(null);
//
//        if (order == null) {
//            return ResponseEntity.badRequest().body("Order not found");
//        }
//
//        order.setStatus(req.getStatus());
//        orderRepo.save(order);
//
//        System.out.println("Nhận callback: " + req.getOrderId() + " -> " + req.getStatus());
//
//        return ResponseEntity.ok("Order updated");
//    }

}

