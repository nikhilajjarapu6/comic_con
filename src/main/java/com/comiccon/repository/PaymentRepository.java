package com.comiccon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.comiccon.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment,Integer> {

}
