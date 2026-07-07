package com.badminton.backend.dto;

import com.badminton.backend.entity.Lapangan;
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
public class LapanganResponse {
    private Long id;
    private String namaLapangan;
    private String deskripsi;
    private Integer biayaSewaPerJam;
    private Boolean isTersedia;
    private Boolean isAktif;

    public static LapanganResponse fromLapangan(Lapangan lapangan) {
        if (lapangan == null) {
            return null;
        }
        return LapanganResponse.builder()
                .id(lapangan.getId())
                .namaLapangan(lapangan.getNamaLapangan())
                .deskripsi(lapangan.getDeskripsi())
                .biayaSewaPerJam(lapangan.getBiayaSewaPerJam())
                .isTersedia(lapangan.getIsTersedia())
                .isAktif(lapangan.getIsAktif())
                .build();
    }
}
