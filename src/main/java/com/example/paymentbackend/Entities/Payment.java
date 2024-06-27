package com.example.paymentbackend.Entities;
import com.example.paymentbackend.Enums.PaymentStatus;
import com.example.paymentbackend.Enums.PaymentType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
@Entity
@NoArgsConstructor @AllArgsConstructor @Getter @Setter @ToString @Builder
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private double amount;
    private PaymentType type;
    private PaymentStatus status=PaymentStatus.CREATED;
    private String file;
    @ManyToOne
    private Student student;
}