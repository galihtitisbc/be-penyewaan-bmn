package com.badminton.backend.service;

import com.badminton.backend.dto.LapanganRequest;
import com.badminton.backend.dto.LapanganResponse;

import java.util.List;

public interface LapanganService {
    LapanganResponse create(LapanganRequest request);
    LapanganResponse update(Long id, LapanganRequest request);
    LapanganResponse findById(Long id);
    List<LapanganResponse> findAll();
    void delete(Long id);
}
