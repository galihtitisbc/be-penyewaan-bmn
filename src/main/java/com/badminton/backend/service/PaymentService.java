package com.badminton.backend.service;

import com.badminton.backend.dto.TransactionTokenResponse;
import com.badminton.backend.dto.midtrans.MidtransRequest;

public interface PaymentService {
    TransactionTokenResponse paymentProcess(MidtransRequest request);
}
