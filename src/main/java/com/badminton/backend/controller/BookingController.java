package com.badminton.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.badminton.backend.dto.BookingRequest;
import com.badminton.backend.dto.BookingResponse;
import com.badminton.backend.security.AuthenticationGetCurrentUser;
import com.badminton.backend.service.BookingService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/booking")
@AllArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @GetMapping
    public ResponseEntity<List<BookingResponse>> getBookingByUserId() {
        Long userId = AuthenticationGetCurrentUser.getCurrentUserId();
        return ResponseEntity.ok().body(bookingService.getBookingsByUserId(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> getBookingById(@PathVariable Long id) {
        return ResponseEntity.ok().body(bookingService.getBookingById(id));
    }

    @GetMapping("/lapangan/{lapanganId}")
    public ResponseEntity<List<BookingResponse>> getBookingByLapanganId(@PathVariable Long lapanganId) {
        return ResponseEntity.ok().body(bookingService.getBookingsByLapanganId(lapanganId));
    }

    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(@RequestBody BookingRequest request) {
        return ResponseEntity.ok().body(bookingService.createBooking(request));
    }
}
