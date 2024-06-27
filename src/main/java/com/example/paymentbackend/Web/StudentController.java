package com.example.paymentbackend.Web;

import com.example.paymentbackend.Entities.Payment;
import com.example.paymentbackend.Entities.Student;
import com.example.paymentbackend.Enums.PaymentStatus;
import com.example.paymentbackend.Enums.PaymentType;
import com.example.paymentbackend.Repositories.PaymentRepository;
import com.example.paymentbackend.Repositories.StudentRepository;
import com.example.paymentbackend.Services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin("*")
public class StudentController {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/payments")
    public List<Payment> allPayments() {
        return paymentRepository.findAll();
    }

    @GetMapping("/payments/{id}")
    public Payment findById(@PathVariable Long id) {
        return paymentRepository.findById(id).orElse(null);
    }

    @GetMapping("/students")
    public List<Student> allStudents() {
        return studentRepository.findAll();
    }

    @GetMapping("/students/{id}")
    public Student findById(@PathVariable String id) {
        return studentRepository.findById(String.valueOf(id)).orElse(null);
    }

    @GetMapping("/students/{code}/payments")
    public List<Payment> paymentsByStudentCode(@PathVariable String code) {
        return paymentRepository.findByStudentCode(code);
    }
    @PostMapping(path="/payments", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Payment savePayment(@RequestParam MultipartFile file, double amount, PaymentType type, LocalDate date, String studentCode) throws IOException {
        return paymentService.savePayment(file,amount,type,date,studentCode);
    }

    @GetMapping(path="/paymentFile/{paymentId}",produces = MediaType.APPLICATION_PDF_VALUE)
    public byte[] getPaymentFile(@PathVariable Long paymentId) throws IOException {
        return paymentService.getPaymentFile(paymentId);
    }
    @PutMapping("/payments/{paymentId}/updateStatus")
    public Payment updatePaymentStatus(@RequestParam PaymentStatus status, @PathVariable Long paymentId){
        return paymentService.updatePaymentStatus(status,paymentId);
    }
    @GetMapping("/studentsByCode/{code}")
    public Student getStudentByCode(@PathVariable String code){
        return studentRepository.findByCode(code);
    }
    @GetMapping("/paymentsByStatus")
    public List<Payment> paymentsByStatus(@RequestParam PaymentStatus status){
        return paymentRepository.findByStatus(status);
    }
    @GetMapping("/paymentsByType")
    public List<Payment> paymentsByType(@RequestParam PaymentType type){
        return paymentRepository.findByType(type);
    }

}
