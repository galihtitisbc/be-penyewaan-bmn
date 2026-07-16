package com.badminton.backend.service.impl;

import org.springframework.stereotype.Service;

import com.badminton.backend.dto.PaymentRequest;
import com.badminton.backend.dto.PaymentResponse;
import com.badminton.backend.dto.TransactionTokenResponse;
import com.badminton.backend.dto.midtrans.MidtransRequest;
import com.badminton.backend.service.PaymentService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    @Override
    public TransactionTokenResponse paymentProcess(MidtransRequest request) {
        System.out.println(request);
        return null;
    }

}
