package com.badminton.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "lapangans")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lapangan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, name = "nama_lapangan")
    private String namaLapangan;

    @Column(nullable = false, name = "deskripsi")
    private String deskripsi;

    @Column(unique = false, nullable = false, name = "biaya_sewa_per_jam")
    private Integer biayaSewaPerJam;

    @Column(nullable = false, name = "is_tersedia")
    private Boolean isTersedia = true;

    @Column(nullable = false, name = "is_aktif")
    private Boolean isAktif = true;

}
