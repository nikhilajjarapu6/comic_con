package com.comiccon.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comiccon.dto.PaymentRequestDto;
import com.comiccon.dto.PaymentResponseDto;
import com.comiccon.entity.OrderStatus;
import com.comiccon.entity.Orders;
import com.comiccon.entity.Payment;
import com.comiccon.exceptions.BadRequestException;
import com.comiccon.exceptions.InvalidOperationException;
import com.comiccon.exceptions.ResourceNotFoundException;
import com.comiccon.mapper.PaymentMapper;
import com.comiccon.repository.OrderRepository;
import com.comiccon.repository.PaymentRepository;
import com.comiccon.service.PaymentService;

import jakarta.transaction.Transactional;

@Service
public class PaymentServiceImpl implements PaymentService {
	
	private final PaymentRepository repo;
	private final OrderRepository orderRepository;
	private final PaymentMapper mapper;
	
	

	public PaymentServiceImpl(PaymentRepository repo, OrderRepository orderRepository, PaymentMapper mapper) {
		super();
		this.repo = repo;
		this.orderRepository = orderRepository;
		this.mapper = mapper;
	}

	@Override
	@Transactional
	public PaymentResponseDto addPayment(PaymentRequestDto dto) {
		if (dto.getAmount() <= 0) {
		    throw new BadRequestException("Payment amount must be greater than zero")
		            .addDetail("amount", dto.getAmount());
		}

		Orders order = orderRepository.findById(dto.getOrderId())
				.orElseThrow(()->new ResourceNotFoundException("Order not found")
			    		.addDetail("orderId",dto.getOrderId()));
		if (order.getOrderStatus() != OrderStatus.CONFIRMED) {
		    throw new InvalidOperationException("Payment not allowed for this order state")
		            .addDetail("status", order.getOrderStatus());
		}

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
				.orElseThrow(()->new ResourceNotFoundException("Payment not found")
			    		.addDetail("paymentId",id));
		
		
		return mapper.toDto(payment);
	}

	@Override
	@Transactional
	public PaymentResponseDto updatePayment(PaymentRequestDto dto, Integer id) {
		if (dto.getAmount() <= 0) {
		    throw new BadRequestException("Payment amount must be greater than zero")
		            .addDetail("amount", dto.getAmount());
		}

		 Payment payment = repo.findById(id)
				 .orElseThrow(()->new ResourceNotFoundException("Payment not found")
				    		.addDetail("paymentId",id));

	    Orders order = orderRepository.findById(dto.getOrderId())
	    		.orElseThrow(()->new ResourceNotFoundException("Order not found")
			    		.addDetail("orderId",dto.getOrderId()));
	    if (order.getOrderStatus() != OrderStatus.CONFIRMED) {
	        throw new InvalidOperationException("Payment not allowed for this order state")
	                .addDetail("status", order.getOrderStatus());
	    }


	    payment.setOrder(order); // set new/updated order
	    payment.setPaymentTime(LocalDateTime.now());

	    mapper.updateFromDto(dto, payment);
	    return mapper.toDto(repo.save(payment));
	}

	@Override
	@Transactional
	public void deletePayment(Integer id) {
		Payment payment = repo.findById(id)
				.orElseThrow(()->new ResourceNotFoundException("Payment not found")
			    		.addDetail("paymentId",id));
		repo.delete(payment);
	}
}
