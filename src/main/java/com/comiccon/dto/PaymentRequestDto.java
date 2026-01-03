package com.comiccon.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PaymentRequestDto {
	
	private Double amount;
	private LocalDateTime paymentTime;
	private String paymentMethod;
	private String paymentStatus;
	private String transactionId;
	private Integer orderId;
	
}
