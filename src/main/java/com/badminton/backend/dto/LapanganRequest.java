package com.badminton.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LapanganRequest {

    @NotBlank(message = "Nama lapangan tidak boleh kosong")
    @Size(max = 100, message = "Nama lapangan maksimal 100 karakter")
    @JsonProperty("nama_lapangan")
    private String namaLapangan;

    @NotBlank(message = "Deskripsi tidak boleh kosong")
    private String deskripsi;

    @NotNull(message = "Biaya sewa per jam tidak boleh kosong")
    @Min(value = 0, message = "Biaya sewa per jam minimal 0")
    @JsonProperty("biaya_sewa_per_jam")
    private Integer biayaSewaPerJam;
    @JsonProperty("is_tersedia")
    private Boolean isTersedia;
    @JsonProperty("is_aktif")
    private Boolean isAktif;
}
