package com.badminton.backend.controller;

import com.badminton.backend.dto.UserResponse;
import com.badminton.backend.security.UserDetailsImpl;
import com.badminton.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserResponse> getUserProfile(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        UserResponse response = userService.findByUsername(userDetails.getUsername());
        return ResponseEntity.ok(response);
    }
}
