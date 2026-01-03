package com.comiccon.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.comiccon.entity.OrderStatus;

import lombok.Data;

@Data
public class OrderResponseDto {

	private Integer id;
	private LocalDateTime orderDate;
	private String orderStatus;
	private Double totalAmount;
	private List<ItemOrderResponseDto> items;
	private UserResponseDto user;
	private AddressResponseDto address;
}
