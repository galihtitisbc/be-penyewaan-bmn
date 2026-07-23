package com.badminton.backend.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.badminton.backend.dto.PaymentRequest;
import com.badminton.backend.dto.TransactionTokenResponse;
import com.badminton.backend.dto.midtrans.CreditCard;
import com.badminton.backend.dto.midtrans.CustomerDetails;
import com.badminton.backend.dto.midtrans.ItemDetail;
import com.badminton.backend.dto.midtrans.MidtransRequest;
import com.badminton.backend.dto.midtrans.TransactionDetails;
import com.badminton.backend.entity.Booking;
import com.badminton.backend.entity.PaymentHistory;
import com.badminton.backend.enums.PaymentStatus;
import com.badminton.backend.enums.StatusBooking;
import com.badminton.backend.exception.BadRequestException;
import com.badminton.backend.repository.BookingRepository;
import com.badminton.backend.repository.PaymentHistoryRepository;
import com.badminton.backend.service.PaymentService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {
        private MidtransService midtransService;
        private BookingRepository bookingRepository;
        private PaymentHistoryRepository paymentHistoryRepository;

        @Override
        public TransactionTokenResponse paymentProcess(PaymentRequest request) {
                Booking booking = bookingRepository.findById(request.getBookingId()).orElseThrow(
                                () -> new RuntimeException("Booking tidak ditemukan"));

                if (booking.getStatusBooking().equals(StatusBooking.PAID)) {
                        throw new RuntimeException("Booking Sudah Di Bayar");
                }
                PaymentHistory paymentHistory = PaymentHistory.builder()
                                .booking(booking)
                                .orderId(generateOrderId())
                                .grossAmount(booking.getTotalBiaya().longValue())
                                .status(PaymentStatus.BELUM_BAYAR)
                                .build();
                paymentHistoryRepository.save(paymentHistory);
                MidtransRequest midtransRequest = buildMidtransRequest(paymentHistory);
                try {
                        String token = midtransService.createTransaction(midtransRequest);
                        TransactionTokenResponse response = new TransactionTokenResponse(token,
                                        null);
                        return response;
                } catch (Exception e) {
                        e.printStackTrace();
                        throw new BadRequestException("Request Ada Yang Salah");
                }
        }

        private String generateOrderId() {
                return "ORD-" + System.currentTimeMillis();
        }

        private MidtransRequest buildMidtransRequest(PaymentHistory paymentHistory) {

                Booking booking = paymentHistory.getBooking();

                TransactionDetails transactionDetails = TransactionDetails.builder()
                                .orderId(paymentHistory.getOrderId())
                                .grossAmount(paymentHistory.getGrossAmount())
                                .build();

                CreditCard creditCard = CreditCard.builder()
                                .secure(true)
                                .build();

                ItemDetail itemDetail = ItemDetail.builder()
                                .id(String.valueOf(booking.getLapangan().getId()))
                                .price(booking.getTotalBiaya().longValue())
                                .quantity(1)
                                .name(booking.getLapangan().getNamaLapangan())
                                .build();

                CustomerDetails customerDetails = CustomerDetails.builder()
                                .firstName(booking.getUser().getFullName())
                                .email(booking.getUser().getEmail())
                                .build();

                return MidtransRequest.builder()
                                .transactionDetails(transactionDetails)
                                .creditCard(creditCard)
                                .itemDetails(List.of(itemDetail))
                                .customerDetails(customerDetails)
                                .build();
        }

        @Override
        public void changeStatusPayment(String orderId) {
                PaymentHistory paymentHistory = paymentHistoryRepository.findByOrderId(orderId).orElseThrow(
                                () -> new RuntimeException("Order Id Tidak Ditemukan"));
                Booking booking = bookingRepository.findById(paymentHistory.getBooking().getId()).orElseThrow(
                                () -> new RuntimeException("Booking tidak ditemukan"));
                booking.setStatusBooking(StatusBooking.PAID);
                paymentHistory.setStatus(PaymentStatus.LUNAS);
                paymentHistory.setBooking(booking);
                paymentHistoryRepository.save(paymentHistory);
        }
}
