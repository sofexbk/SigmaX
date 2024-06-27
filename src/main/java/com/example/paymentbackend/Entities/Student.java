package com.example.paymentbackend.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.transaction.annotation.Transactional;

@Entity
@NoArgsConstructor @AllArgsConstructor @Getter @Setter @ToString @Builder
@Table(name = "student")
public class Student {
    @Id
    private String id;
    @Column(unique = true)
    private String code;
    private String firstName;
    private String lastName;
    private String programId;
    private String photo;
}