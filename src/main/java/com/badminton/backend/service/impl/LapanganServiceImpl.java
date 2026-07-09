package com.badminton.backend.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.badminton.backend.exception.BadRequestException;
import com.badminton.backend.exception.ResourceNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.badminton.backend.dto.LapanganRequest;
import com.badminton.backend.dto.LapanganResponse;
import com.badminton.backend.entity.Lapangan;
import com.badminton.backend.repository.LapanganRepository;
import com.badminton.backend.service.LapanganService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LapanganServiceImpl implements LapanganService {

    private LapanganRepository lapanganRepository;

    @Override
    public LapanganResponse create(LapanganRequest request) {
        boolean existLapangan = this.lapanganRepository.existsByNamaLapangan(request.getNamaLapangan());
        if (existLapangan) {
            throw new BadRequestException("Lapangan Dengan Nama " + request.getNamaLapangan() + " Sudah Terdaftar");
        }
        Lapangan lapangan = Lapangan.builder()
                .namaLapangan(request.getNamaLapangan())
                .deskripsi(request.getDeskripsi())
                .biayaSewaPerJam(request.getBiayaSewaPerJam())
                .isAktif(true)
                .isTersedia(true)
                .build();
        this.lapanganRepository.save(lapangan);
        return LapanganResponse.fromLapangan(lapangan);
    }

    @Override
    @Transactional
    public LapanganResponse update(Long id, LapanganRequest request) {
        Lapangan lapangan = this.lapanganRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data Tidak Ditemukan"));
        lapangan.setNamaLapangan(request.getNamaLapangan());
        lapangan.setDeskripsi(request.getDeskripsi());
        lapangan.setBiayaSewaPerJam(request.getBiayaSewaPerJam());
        lapangan.setIsAktif(request.getIsAktif());
        lapangan.setIsTersedia(request.getIsTersedia());

        return LapanganResponse.fromLapangan(lapangan);
    }

    @Override
    public LapanganResponse findById(Long id) {
        Lapangan lapangan = this.lapanganRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data Lapangan Tidak Ditemukan"));
        return LapanganResponse.fromLapangan(lapangan);
    }

    @Override
    public List<LapanganResponse> findAll() {
        List<Lapangan> lapangans = lapanganRepository.findAll();
        List<LapanganResponse> res = lapangans.stream().map((lap) -> {
            LapanganResponse lapanganResponse = LapanganResponse.fromLapangan(lap);
            return lapanganResponse;
        }).collect(Collectors.toList());
        return res;
    }

    @Override
    public void delete(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

}
