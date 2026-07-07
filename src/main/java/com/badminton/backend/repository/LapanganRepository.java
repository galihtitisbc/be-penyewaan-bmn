package com.badminton.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.badminton.backend.entity.Lapangan;

public interface LapanganRepository extends JpaRepository<Lapangan, Long> {
    boolean existsByNamaLapangan(String namaLapangan);
    boolean existsByNamaLapanganAndIdNot(String namaLapangan, Long id);
}
