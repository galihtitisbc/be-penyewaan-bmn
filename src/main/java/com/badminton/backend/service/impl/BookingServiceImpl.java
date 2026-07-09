package com.badminton.backend.service.impl;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.badminton.backend.dto.BookingRequest;
import com.badminton.backend.dto.BookingResponse;
import com.badminton.backend.dto.LapanganResponse;
import com.badminton.backend.entity.Booking;
import com.badminton.backend.enums.StatusBooking;
import com.badminton.backend.exception.BusinessValidationException;
import com.badminton.backend.repository.BookingRepository;
import com.badminton.backend.security.AuthenticationGetCurrentUser;
import com.badminton.backend.service.BookingService;
import com.badminton.backend.service.LapanganService;
import com.badminton.backend.service.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final LapanganService lapanganService;
    private final UserService userService;

    @Override
    @Transactional
    public BookingResponse createBooking(BookingRequest bookingRequest) {
        // validasi jam
        if (!bookingRequest.getJamSelesai().isAfter(bookingRequest.getJamMulai())) {
            throw new BusinessValidationException("Jam selesai harus lebih besar dari jam mulai.");
        }
        LapanganResponse lapangan = lapanganService.findById(bookingRequest.getLapanganId());
        if (!lapangan.getIsAktif() || !lapangan.getIsTersedia()) {
            throw new BusinessValidationException("Mohon maaf, lapangan sedang tidak tersedia atau dinonaktifkan.");
        }
        // validasi jadwal
        boolean isBentrok = this.isJadwalTersedia(
                bookingRequest.getLapanganId(),
                bookingRequest.getTanggalSewa(),
                bookingRequest.getJamMulai(),
                bookingRequest.getJamSelesai());
        if (isBentrok) {
            throw new BusinessValidationException("Jadwal sudah dibooking orang lain pada waktu tersebut.");
        }
        // hitung total biaya dari durasi
        long durationInMinutes = Duration.between(bookingRequest.getJamMulai(), bookingRequest.getJamSelesai())
                .toMinutes();
        int jamSewa = (int) Math.ceil(durationInMinutes / 60.0);
        Integer totalBiaya = jamSewa * lapangan.getBiayaSewaPerJam();
        Booking booking = new Booking();
        booking.setLapangan(LapanganResponse.toLapangan(lapangan));
        booking.setUser(userService.getReferenceById(AuthenticationGetCurrentUser.getCurrentUserId()));
        booking.setTanggalSewa(bookingRequest.getTanggalSewa());
        booking.setJamMulai(bookingRequest.getJamMulai());
        booking.setJamSelesai(bookingRequest.getJamSelesai());
        booking.setTotalBiaya(totalBiaya);
        booking.setStatusBooking(StatusBooking.CONFIRMED);

        Booking savedBooking = bookingRepository.save(booking);
        return BookingResponse.builder()
                .id(savedBooking.getId())
                .lapanganId(savedBooking.getLapangan().getId())
                .namaLapangan(savedBooking.getLapangan().getNamaLapangan())
                .userId(savedBooking.getUser().getId())
                .tanggalSewa(savedBooking.getTanggalSewa())
                .jamMulai(savedBooking.getJamMulai())
                .jamSelesai(savedBooking.getJamSelesai())
                .totalBiaya(savedBooking.getTotalBiaya())
                .statusBooking(savedBooking.getStatusBooking().name())
                .build();
    }

    @Override
    public void cancelBooking(Long bookingId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cancelBooking'");
    }

    @Override
    public BookingResponse getBookingById(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking Tidak DItemukan id: " + bookingId));
        return BookingResponse.builder()
                .id(booking.getId())
                .lapanganId(booking.getLapangan().getId())
                .namaLapangan(booking.getLapangan().getNamaLapangan())
                .userId(booking.getUser().getId())
                .tanggalSewa(booking.getTanggalSewa())
                .jamMulai(booking.getJamMulai())
                .jamSelesai(booking.getJamSelesai())
                .totalBiaya(booking.getTotalBiaya())
                .statusBooking(booking.getStatusBooking().name())
                .build();
    }

    @Override
    public List<BookingResponse> getBookingsByUserId(Long userId) {
        List<Booking> bookings = bookingRepository.findByUserId(userId);
        List<BookingResponse> bookingResponses = bookings.stream()
                .map(b -> {
                    BookingResponse res = BookingResponse.builder()
                            .id(b.getId())
                            .lapanganId(b.getLapangan().getId())
                            .namaLapangan(b.getLapangan().getNamaLapangan())
                            .userId(b.getUser().getId())
                            .tanggalSewa(b.getTanggalSewa())
                            .jamMulai(b.getJamMulai())
                            .jamSelesai(b.getJamSelesai())
                            .totalBiaya(b.getTotalBiaya())
                            .statusBooking(b.getStatusBooking().name())
                            .build();
                    return res;
                })
                .collect(Collectors.toList());
        return bookingResponses;
    }

    @Override
    public boolean isJadwalTersedia(Long lapanganId, LocalDate tanggal, LocalTime mulai, LocalTime selesai) {
        boolean isBentrok = bookingRepository.existsConflictingBooking(
                lapanganId,
                tanggal,
                mulai,
                selesai);
        return isBentrok;
    }

    @Override
    public List<BookingResponse> getBookingsByLapanganId(Long lapanganId) {
        List<Booking> bookings = bookingRepository.findByLapanganId(lapanganId);
        List<BookingResponse> bookingResponses = bookings.stream()
                .map(b -> {
                    BookingResponse res = BookingResponse.builder()
                            .id(b.getId())
                            .lapanganId(b.getLapangan().getId())
                            .namaLapangan(b.getLapangan().getNamaLapangan())
                            .tanggalSewa(b.getTanggalSewa())
                            .jamMulai(b.getJamMulai())
                            .jamSelesai(b.getJamSelesai())
                            .totalBiaya(b.getTotalBiaya())
                            .statusBooking(b.getStatusBooking().name())
                            .build();
                    return res;
                })
                .collect(Collectors.toList());
        return bookingResponses;
    }

}
