package com.badminton.backend.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Id;

import com.badminton.backend.enums.StatusBooking;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "booking")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lapangan_id", nullable = false)
    private Lapangan lapangan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, name = "tanggal_sewa")
    private LocalDate tanggalSewa;

    @Column(nullable = false, name = "jam_mulai")
    private LocalTime jamMulai;

    @Column(nullable = false, name = "jam_selesai")
    private LocalTime jamSelesai;

    @Column(nullable = false, name = "total_biaya")
    private Integer totalBiaya;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "status_booking")
    private StatusBooking statusBooking = StatusBooking.CONFIRMED;
}
