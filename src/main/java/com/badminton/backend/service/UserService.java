package com.badminton.backend.service;

import com.badminton.backend.dto.RegisterRequest;
import com.badminton.backend.dto.UserResponse;
import com.badminton.backend.entity.User;

public interface UserService {
    UserResponse register(RegisterRequest request);

    UserResponse findByUsername(String username);

    User getReferenceById(Long id);
}
