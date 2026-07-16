package com.badminton.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.badminton.backend.dto.TransactionTokenResponse;
import com.badminton.backend.dto.midtrans.MidtransRequest;
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
    public ResponseEntity<TransactionTokenResponse> payment(@Valid @RequestBody MidtransRequest request) {
        System.out.println(request);
        return ResponseEntity.ok().body(paymentService.paymentProcess(request));
    }
}
