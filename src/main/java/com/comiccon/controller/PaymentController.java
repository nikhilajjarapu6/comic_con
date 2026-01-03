package com.comiccon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.comiccon.dto.PaymentRequestDto;
import com.comiccon.dto.PaymentResponseDto;
import com.comiccon.service.PaymentService;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponseDto> addPayment(@RequestBody PaymentRequestDto dto) {
        return ResponseEntity.ok(paymentService.addPayment(dto));
    }

    @GetMapping
    public ResponseEntity<List<PaymentResponseDto>> listPayments() {
        return ResponseEntity.ok(paymentService.listOfPayments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponseDto> getPaymentById(@PathVariable Integer id) {
        return ResponseEntity.ok(paymentService.findPaymentById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentResponseDto> updatePayment(@RequestBody PaymentRequestDto dto, @PathVariable Integer id) {
        return ResponseEntity.ok(paymentService.updatePayment(dto, id));
    }

    @DeleteMapping("/{id}") 
    public ResponseEntity<Void> deletePayment(@PathVariable Integer id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    } 
}
