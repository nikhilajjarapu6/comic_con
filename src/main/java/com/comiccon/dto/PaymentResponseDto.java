package com.comiccon.dto;

import java.time.LocalDateTime;

import com.comiccon.entity.PaymentMethod;
import com.comiccon.entity.PaymentStatus;

import lombok.Data;

@Data
public class PaymentResponseDto {
	
	private Integer id;
	private Double amount;
	private LocalDateTime paymentTime;
	private PaymentMethod paymentMethod;
	private PaymentStatus paymentStatus;
	private String transactionId;
	private Integer orderId;
}
