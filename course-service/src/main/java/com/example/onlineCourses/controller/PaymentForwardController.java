package com.example.onlineCourses.controller;

import com.example.onlineCourses.model.VNPayRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/payment")
public class PaymentForwardController {

    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> forwardToGateway(@RequestBody VNPayRequest request) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<VNPayRequest> entity = new HttpEntity<>(request, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(
                "http://localhost:8081/payment/vnpay/create", entity, Map.class);

        // chỉ lấy paymentUrl từ response của Payment Service
        String paymentUrl = (String) response.getBody().get("paymentUrl");

        return ResponseEntity.ok(Map.of("paymentUrl", paymentUrl));
    }


    @PostMapping("/api/payment/ipn")
    public ResponseEntity<?> handleVNPayCallback(@RequestBody Map<String, String> payload) {
        String orderId = payload.get("vnp_TxnRef");
        String status = payload.get("vnp_TransactionStatus");


        // Cập nhật trạng thái đơn hàng trong DB
        return ResponseEntity.ok("IPN received");
    }

}

