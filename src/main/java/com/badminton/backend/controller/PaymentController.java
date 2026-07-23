package com.badminton.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.badminton.backend.dto.PaymentRequest;
import com.badminton.backend.dto.TransactionTokenResponse;
import com.badminton.backend.service.PaymentService;

import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/payment")
@AllArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/pay")
    public ResponseEntity<TransactionTokenResponse> payment(@Valid @RequestBody PaymentRequest request) {
        return ResponseEntity.ok().body(paymentService.paymentProcess(request));
    }

    @GetMapping("/pay/{orderId}")
    public ResponseEntity<String> changeStatusPayment(@PathVariable String orderId) {
        paymentService.changeStatusPayment(orderId);
        return ResponseEntity.ok().body("Berhasil Mengubah Status");
    }
}
