package com.badminton.backend.entity;

import java.time.LocalDateTime;

import com.badminton.backend.enums.PaymentMethod;
import com.badminton.backend.enums.PaymentStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false, unique = true)
    private Booking booking;

    @Column(nullable = false, name = "waktu_pembayaran")
    private LocalDateTime waktuPembayaran = LocalDateTime.now();

    @Column(nullable = false, name = "jumlah_bayar")
    private Integer jumlahBayar;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "metode_pembayaran")
    private PaymentMethod metodePembayaran;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "status_pembayaran")
    private PaymentStatus statusPembayaran = PaymentStatus.BELUM_BAYAR;
}
