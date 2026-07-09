package com.badminton.backend.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class BookingResponse {
    private Long id;
    private Long userId;
    private Long lapanganId;
    private String namaLapangan;
    private LocalDate tanggalSewa;
    private LocalTime jamMulai;
    private LocalTime jamSelesai;
    private Integer totalBiaya;
    private String statusBooking;
}
