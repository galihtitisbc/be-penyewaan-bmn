package com.badminton.backend.service;

import com.badminton.backend.dto.PaymentRequest;
import com.badminton.backend.dto.TransactionTokenResponse;

public interface PaymentService {
    TransactionTokenResponse paymentProcess(PaymentRequest request);

    void changeStatusPayment(String orderId);
}
