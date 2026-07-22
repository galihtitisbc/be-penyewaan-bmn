package com.badminton.backend.service.impl;

import org.springframework.stereotype.Service;

import com.badminton.backend.dto.TransactionTokenResponse;
import com.badminton.backend.dto.midtrans.MidtransRequest;
import com.badminton.backend.exception.BadRequestException;
import com.badminton.backend.service.PaymentService;
import com.midtrans.httpclient.error.MidtransError;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private MidtransService midtransService;

    @Override
    public TransactionTokenResponse paymentProcess(MidtransRequest request) {
        try {
            String token = midtransService.createTransaction(request);
            TransactionTokenResponse response = new TransactionTokenResponse(token, null);
            return response;
        } catch (MidtransError e) {
            e.printStackTrace();
            throw new BadRequestException("Request Ada Yang Salah");
        }
    }

}
