package com.oguzhanbilgi.reservationsystem.reservation_system.controller;

import com.oguzhanbilgi.reservationsystem.reservation_system.model.User;
import com.oguzhanbilgi.reservationsystem.reservation_system.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping("/{email}")
    public User getUserByEmail(@PathVariable String email) {
        return userService.findByEmail(email);
    }
    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody Map<String, String> body) {
        String oldPassword = body.get("oldPassword");
        String newPassword = body.get("newPassword");

        // JWT'den email'i al
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        User user = userService.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(404).body("Kullanıcı bulunamadı");
        }

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            return ResponseEntity.status(400).body("Eski şifre yanlış");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userService.createUser(user); // veya userRepository.save(user)
        return ResponseEntity.ok("Şifre başarıyla değiştirildi");
    }
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = userService.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(404).body("Kullanıcı bulunamadı");
        }
        // Güvenlik için şifreyi null yapabilirsin:
        user.setPassword(null);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/make-admin")
    public ResponseEntity<?> makeAdmin(@RequestParam String email) {
        User user = userService.makeAdmin(email);
        return ResponseEntity.ok().body("User " + email + " is now an admin");
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String newPassword = body.get("newPassword");
        
        try {
            userService.resetPassword(email, newPassword);
            return ResponseEntity.ok().body("Şifre başarıyla sıfırlandı");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Kullanıcı bulunamadı");
        }
    }
}