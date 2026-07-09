package com.badminton.backend.repository;

import com.badminton.backend.entity.Booking;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("""
                SELECT b
                FROM Booking b
                JOIN FETCH b.user
                JOIN FETCH b.lapangan
                WHERE b.user.id = :userId
            """)
    List<Booking> findByUserId(@Param("userId") Long userId);

    @Query("""
                SELECT b
                FROM Booking b
                JOIN FETCH b.user
                JOIN FETCH b.lapangan
                WHERE b.lapangan.id = :lapanganId
            """)
    List<Booking> findByLapanganId(@Param("lapanganId") Long lapanganId);

    @Query("""
            SELECT CASE WHEN COUNT(b) > 0 THEN TRUE ELSE FALSE END
            FROM Booking b
            WHERE b.lapangan.id = :lapanganId
            AND b.tanggalSewa = :tanggalSewa
            AND b.statusBooking != 'CANCELLED'
            AND(
                b.jamMulai < :jamSelesai AND b.jamSelesai > :jamMulai
            )
            """)
    Boolean existsConflictingBooking(
            @Param("lapanganId") Long lapanganId,
            @Param("tanggalSewa") java.time.LocalDate tanggalSewa,
            @Param("jamMulai") java.time.LocalTime jamMulai,
            @Param("jamSelesai") java.time.LocalTime jamSelesai);
}
