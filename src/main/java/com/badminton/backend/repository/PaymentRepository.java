package com.badminton.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.badminton.backend.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
