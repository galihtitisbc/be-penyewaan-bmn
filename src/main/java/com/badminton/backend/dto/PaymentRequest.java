package com.badminton.backend.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class PaymentRequest {
    @NotNull(message = "ID Booking tidak boleh kosong")
    @JsonAlias("booking_id")
    private Long bookingId;

    @NotNull(message = "Jumlah bayar tidak boleh kosong")
    @Min(value = 10000, message = "Minimal pembayaran adalah Rp10.000")
    @JsonAlias("jumlah_bayar")
    private Integer jumlahBayar;

    @NotBlank(message = "Metode pembayaran harus diisi")
    @JsonAlias("metode_pembayaran")
    private String metodePembayaran;
}
