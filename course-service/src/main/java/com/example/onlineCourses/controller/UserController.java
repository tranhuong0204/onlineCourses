package com.example.onlineCourses.controller;

import com.example.onlineCourses.DTO.LoginRequestDTO;
import com.example.onlineCourses.DTO.UserDTO;
import com.example.onlineCourses.mapper.UserMapper;
import com.example.onlineCourses.model.User;
import com.example.onlineCourses.repository.UserRepository;
import com.example.onlineCourses.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserService userService;

    // Đăng ký tài khoản
//    @PostMapping("/register")
//    public ResponseEntity<?> register(@RequestBody User user) {
//        if (userService.emailExists(user.getEmail())) {
//            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email đã tồn tại");
//        }
//        userService.registerUser(user);
//        return ResponseEntity.ok("Đăng ký thành công, vui lòng kiểm tra email để xác thực");
//    }
    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody User user) {
        if (userService.emailExists(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        User savedUser = userService.registerUser(user);
        return ResponseEntity.ok(UserMapper.toDTO(savedUser));
    }


    // Xác thực email
    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestParam String email, @RequestParam String code) {
        //boolean result = userService.verifyOtp(email, code);
        //return ResponseEntity.ok(result ? "Xác thực thành công" : "Xác thực thất bại");
        try {
            boolean result = userService.verifyOtp(email, code);
            if (result) {
                return ResponseEntity.ok("✅ Xác thực thành công");
            } else {
                return ResponseEntity.badRequest().body("❌ Mã xác thực không đúng");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("❌ Lỗi xác thực: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody LoginRequestDTO loginDTO) {
        try {
            User user = userService.login(loginDTO.getEmail(), loginDTO.getPassword());
            UserDTO userDTO = UserMapper.toDTO(user); // chuyển sang DTO nếu cần
            return ResponseEntity.ok(userDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    // Lấy tất cả người dùng
    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    // Lấy người dùng theo ID
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    // Cập nhật thông tin người dùng
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        userService.updateUser(id, userDTO);
        return ResponseEntity.ok("Cập nhật thành công");
    }

    // Xóa người dùng
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("Xóa thành công");
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleNotFound(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

}

