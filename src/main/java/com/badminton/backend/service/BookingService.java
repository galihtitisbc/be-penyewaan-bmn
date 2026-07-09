package com.badminton.backend.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.badminton.backend.dto.BookingRequest;
import com.badminton.backend.dto.BookingResponse;

public interface BookingService {
    BookingResponse createBooking(BookingRequest bookingRequest);

    void cancelBooking(Long bookingId);

    BookingResponse getBookingById(Long bookingId);

    List<BookingResponse> getBookingsByUserId(Long userId);

    List<BookingResponse> getBookingsByLapanganId(Long lapanganId);

    boolean isJadwalTersedia(Long lapanganId, LocalDate tanggal, LocalTime mulai,
            LocalTime selesai);
}
