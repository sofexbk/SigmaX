package com.example.paymentbackend.Services;

import com.example.paymentbackend.Entities.Payment;
import com.example.paymentbackend.Entities.Student;
import com.example.paymentbackend.Enums.PaymentStatus;
import com.example.paymentbackend.Enums.PaymentType;
import com.example.paymentbackend.Repositories.PaymentRepository;
import com.example.paymentbackend.Repositories.StudentRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.UUID;

@Service
@Transactional
public class PaymentService {
    private StudentRepository studentRepository;
    private PaymentRepository paymentRepository;
    public PaymentService(StudentRepository studentRepository, PaymentRepository paymentRepository) {
        this.studentRepository = studentRepository;
        this.paymentRepository = paymentRepository;
    }
    public Payment savePayment(MultipartFile file, double amount, PaymentType type, LocalDate date, String studentCode) throws IOException {
        Path path = Paths.get(System.getProperty("user.home"), "students-app-files", "payments");
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
        String fileId = UUID.randomUUID().toString();
        Path filePath = Paths.get(System.getProperty("user.home"), "students-app-files", "payments", fileId + ".pdf");
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, filePath);
        }
        Student student = studentRepository.findByCode(studentCode);
        Payment payment = Payment.builder()
                .type(type)
                .amount(amount)
                .student(student)
                .status(PaymentStatus.CREATED)
                .file(filePath.toUri().toString())
                .build();
        Payment savedPayment = paymentRepository.save(payment);
        return savedPayment;
    }
    public byte[] getPaymentFile(Long paymentId) throws IOException {
        Payment payment=paymentRepository.findById(paymentId).get();
        String filePath=payment.getFile();
        return Files.readAllBytes(Path.of(URI.create(filePath)));
    }
    public Payment updatePaymentStatus(PaymentStatus status, Long paymentId){
        Payment payment = paymentRepository.findById(paymentId).get();
        payment.setStatus(status);
        return paymentRepository.save(payment);
    }
}
