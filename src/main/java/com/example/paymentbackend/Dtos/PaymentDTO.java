package com.example.paymentbackend.Dtos;
import com.example.paymentbackend.Entities.Student;
import com.example.paymentbackend.Enums.PaymentStatus;
import com.example.paymentbackend.Enums.PaymentType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
@NoArgsConstructor @AllArgsConstructor @Getter @Setter @ToString @Builder
public class PaymentDTO {
    private Long id;
    private LocalDate date;
    private double amount;
    private PaymentType type;
    private PaymentStatus status=PaymentStatus.CREATED;
}