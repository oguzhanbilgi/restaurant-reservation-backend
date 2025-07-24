package com.oguzhanbilgi.reservationsystem.reservation_system.controller;

import com.oguzhanbilgi.reservationsystem.reservation_system.model.User;
import com.oguzhanbilgi.reservationsystem.reservation_system.security.JwtUtil;
import com.oguzhanbilgi.reservationsystem.reservation_system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        try {
            String email = credentials.get("email");
            String password = credentials.get("password");
            
            if (email == null || password == null) {
                return ResponseEntity.badRequest()
                    .body(Map.of("error", "Email and password are required"));
            }

            User user = userService.findByEmail(email);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid email or password"));
            }

            if (!passwordEncoder.matches(password, user.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid email or password"));
            }

            String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
            
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("user", Map.of(
                "id", user.getId(),
                "email", user.getEmail(),
                "name", user.getName(),
                "role", user.getRole().toUpperCase() // Role'ü büyük harfe çeviriyoruz
            ));
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "An error occurred during login"));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            if (userService.findByEmail(user.getEmail()) != null) {
                return ResponseEntity.badRequest()
                    .body(Map.of("error", "Email already exists"));
            }

            // Şifreyi hashle
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            
            // Varsayılan rol ata
            user.setRole("USER");

            User createdUser = userService.createUser(user);
            String token = jwtUtil.generateToken(createdUser.getEmail(), createdUser.getRole());
            
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("user", Map.of(
                "id", createdUser.getId(),
                "email", createdUser.getEmail(),
                "name", createdUser.getName(),
                "role", createdUser.getRole().toUpperCase() // Role'ü büyük harfe çeviriyoruz
            ));
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "An error occurred during registration"));
        }
    }
}