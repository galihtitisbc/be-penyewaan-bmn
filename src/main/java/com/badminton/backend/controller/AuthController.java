package com.badminton.backend.controller;

import com.badminton.backend.dto.LoginRequest;
import com.badminton.backend.dto.RegisterRequest;
import com.badminton.backend.dto.TokenResponse;
import com.badminton.backend.dto.UserResponse;
import com.badminton.backend.security.JwtUtils;
import com.badminton.backend.security.UserDetailsImpl;
import com.badminton.backend.service.UserService;

import jakarta.validation.Valid;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

        @Autowired
        private AuthenticationManager authenticationManager;

        @Autowired
        private UserService userService;

        @Autowired
        private JwtUtils jwtUtils;

        @PostMapping("/register")
        public ResponseEntity<UserResponse> registerUser(@RequestBody @Valid RegisterRequest registerRequest) {
                UserResponse response = userService.register(registerRequest);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
        }

        @PostMapping("/login")
        public ResponseEntity<TokenResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
                Authentication authentication = authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                                                loginRequest.getPassword()));

                SecurityContextHolder.getContext().setAuthentication(authentication);
                UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
                String jwtToken = jwtUtils.generateJwtToken(userDetails.getUsername());
                String refreshToken = jwtUtils.generateRefreshToken(userDetails.getUsername());
                ResponseCookie refreshTokenCookie = jwtUtils.createCookieRefreshToken(refreshToken);
                TokenResponse tokenResponse = TokenResponse.builder()
                                .accessToken(jwtToken)
                                .refreshToken(refreshTokenCookie.getValue())
                                .build();
                return ResponseEntity.ok()
                                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                                .body(tokenResponse);
        }

        @PostMapping("/refresh-token")
        public ResponseEntity<?> refreshToken(
                        @CookieValue(name = "refresh_token", required = false) String cookieRefreshToken,
                        @RequestBody(required = false) Map<String, String> bodyRefreshToken) {

                String refreshToken = cookieRefreshToken;
                if (refreshToken == null && bodyRefreshToken != null) {
                        refreshToken = bodyRefreshToken.get("refreshToken");
                }
                try {
                        if (!jwtUtils.validateRefreshToken(refreshToken)) {
                                System.out.println(refreshToken);
                                throw new BadRequestException("Refresh Token Tidak Valid");
                        }
                        String username = jwtUtils.getUserNameFromJwtToken(refreshToken);
                        String newAccessToken = jwtUtils.generateJwtToken(username);
                        String newRefreshToken = jwtUtils.generateRefreshToken(username);
                        ResponseCookie refreshTokenCookie = jwtUtils.createCookieRefreshToken(newRefreshToken);
                        TokenResponse tokenResponse = TokenResponse.builder()
                                        .accessToken(newAccessToken)
                                        .refreshToken(newRefreshToken)
                                        .build();
                        return ResponseEntity.ok()
                                        .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                                        .body(tokenResponse);
                } catch (Exception e) {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token tidak valid");
                }
        }

        @PostMapping("/logout")
        public ResponseEntity<?> logoutUser() {
                return ResponseEntity.ok(
                                Map.of("message", "Logout."));
        }
}
