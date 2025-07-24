package com.oguzhanbilgi.reservationsystem.reservation_system.controller;

import com.oguzhanbilgi.reservationsystem.reservation_system.model.User;
import com.oguzhanbilgi.reservationsystem.reservation_system.repository.UserRepository;
import com.oguzhanbilgi.reservationsystem.reservation_system.security.JwtUtil;
import com.oguzhanbilgi.reservationsystem.reservation_system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        User dbUser = userService.findByEmail(user.getEmail());
        if (dbUser != null && passwordEncoder.matches(user.getPassword(), dbUser.getPassword())) {
            String token = jwtUtil.generateToken(dbUser.getEmail(), dbUser.getRole());
            return ResponseEntity.ok().body(Map.of("token", token));
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }

    @GetMapping("/encode")
    public String encodePassword() {
        return passwordEncoder.encode("852147");
    }
}