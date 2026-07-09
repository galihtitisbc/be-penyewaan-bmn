package com.badminton.backend.service;

import com.badminton.backend.dto.PaymentRequest;
import com.badminton.backend.dto.PaymentResponse;

public interface PaymentService {
    PaymentResponse prosesPembayaran(PaymentRequest request);

    PaymentResponse getPembayaranByBookingId(Long bookingId);

    void batalkanPembayaran(Long pembayaranId);
}
