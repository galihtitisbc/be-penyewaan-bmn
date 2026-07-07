package com.badminton.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.badminton.backend.dto.LapanganRequest;
import com.badminton.backend.dto.LapanganResponse;
import com.badminton.backend.service.LapanganService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/lapangan")
public class LapanganController {
    private LapanganService lapanganService;

    @GetMapping
    public ResponseEntity<List<LapanganResponse>> findAll() {
        return ResponseEntity.ok(lapanganService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LapanganResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(lapanganService.findById(id));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public LapanganResponse update(@RequestBody @Valid LapanganRequest request, @PathVariable Long id) {
        return lapanganService.update(id, request);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public LapanganResponse create(@RequestBody @Valid LapanganRequest request) {
        return lapanganService.create(request);
    }
}
