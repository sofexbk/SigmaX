package com.example.paymentbackend.Repositories;

import com.example.paymentbackend.Entities.Payment;
import com.example.paymentbackend.Enums.PaymentStatus;
import com.example.paymentbackend.Enums.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
 List<Payment> findByStudentCode(String code);
 List<Payment> findByStatus(PaymentStatus status);
 List<Payment> findByType(PaymentType type);
}