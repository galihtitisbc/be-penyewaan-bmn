package com.badminton.backend.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class PaymentResponse {
    private Long id;
    private Long bookingId;
    private LocalDateTime waktuPembayaran;
    private Integer jumlahBayar;
    private String metodePembayaran;
    private String statusPembayaran;
}
