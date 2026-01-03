package com.comiccon.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comiccon.dto.PaymentRequestDto;
import com.comiccon.dto.PaymentResponseDto;
import com.comiccon.entity.Orders;
import com.comiccon.entity.Payment;
import com.comiccon.mapper.PaymentMapper;
import com.comiccon.repository.OrderRepository;
import com.comiccon.repository.PaymentRepository;
import com.comiccon.service.PaymentService;

import jakarta.transaction.Transactional;

@Service
public class PaymentServiceImpl implements PaymentService {
	
	@Autowired
	PaymentRepository repo;
	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	PaymentMapper mapper;

	@Override
	@Transactional
	public PaymentResponseDto addPayment(PaymentRequestDto dto) {
		
		Orders order = orderRepository.findById(dto.getOrderId())
					   .orElseThrow(()->new RuntimeException("order not found"));
		Payment payment = mapper.toEntity(dto);
		payment.setPaymentTime(LocalDateTime.now());
		payment.setOrder(order);
		
		
		return mapper.toDto(repo.save(payment));
	}

	@Override
	public List<PaymentResponseDto> listOfPayments() {
		return repo.findAll().stream()
					     .map(mapper::toDto)
					     .collect(Collectors.toList());
	}

	@Override
	public PaymentResponseDto findPaymentById(Integer id) {
		Payment payment = repo.findById(id)
			.orElseThrow(()->new RuntimeException("payment not found"));
		
		
		return mapper.toDto(payment);
	}

	@Override
	@Transactional
	public PaymentResponseDto updatePayment(PaymentRequestDto dto, Integer id) {
		 Payment payment = repo.findById(id)
	                .orElseThrow(() -> new RuntimeException("payment not found"));

	    Orders order = orderRepository.findById(dto.getOrderId())
	                .orElseThrow(() -> new RuntimeException("order not found"));

	    payment.setOrder(order); // set new/updated order
	    payment.setPaymentTime(LocalDateTime.now());

	    mapper.updateFromDto(dto, payment);
	    return mapper.toDto(repo.save(payment));
	}

	@Override
	@Transactional
	public void deletePayment(Integer id) {
		Payment payment = repo.findById(id)
				.orElseThrow(()->new RuntimeException("payment not found"));
		repo.delete(payment);
	}
}
