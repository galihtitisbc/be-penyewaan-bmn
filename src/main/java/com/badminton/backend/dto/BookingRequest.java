package com.badminton.backend.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonAlias;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class BookingRequest {
    @NotNull(message = "ID Lapangan tidak boleh kosong")
    @JsonAlias("lapangan_id")
    private Long lapanganId;

    @NotNull(message = "Tanggal sewa tidak boleh kosong")
    @FutureOrPresent(message = "Tanggal sewa harus hari ini atau di masa depan")
    @JsonAlias("tanggal_sewa")
    private LocalDate tanggalSewa;

    @NotNull(message = "Jam mulai tidak boleh kosong")
    @JsonAlias("jam_mulai")
    private LocalTime jamMulai;

    @NotNull(message = "Jam selesai tidak boleh kosong")
    @JsonAlias("jam_selesai")
    private LocalTime jamSelesai;
}
