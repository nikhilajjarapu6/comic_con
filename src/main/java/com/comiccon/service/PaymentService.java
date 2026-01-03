package com.comiccon.service;

import java.util.List;

import com.comiccon.dto.PaymentRequestDto;
import com.comiccon.dto.PaymentResponseDto;

public interface PaymentService {
	
	PaymentResponseDto addPayment(PaymentRequestDto dto);
	List<PaymentResponseDto> listOfPayments();
	PaymentResponseDto findPaymentById(Integer id);
	PaymentResponseDto updatePayment(PaymentRequestDto dto, Integer id);
	void deletePayment(Integer id);
}
