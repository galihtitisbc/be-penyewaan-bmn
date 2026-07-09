package com.badminton.backend.service.impl;

import org.springframework.stereotype.Service;

import com.badminton.backend.dto.PaymentRequest;
import com.badminton.backend.dto.PaymentResponse;
import com.badminton.backend.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Override
    public PaymentResponse prosesPembayaran(PaymentRequest request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'prosesPembayaran'");
    }

    @Override
    public PaymentResponse getPembayaranByBookingId(Long bookingId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPembayaranByBookingId'");
    }

    @Override
    public void batalkanPembayaran(Long pembayaranId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'batalkanPembayaran'");
    }

}
