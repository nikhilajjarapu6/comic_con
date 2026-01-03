package com.comiccon.service;

import java.util.List;

import com.comiccon.dto.OrderRequestDto;
import com.comiccon.dto.OrderResponseDto;

public interface OrderService {
	OrderResponseDto placeOrder(OrderRequestDto dto);
	List<OrderResponseDto> listOfOrders();
	OrderResponseDto findOrderById(Integer id);
	OrderResponseDto updateOrder(OrderRequestDto dto,Integer id);
	void deleteOrder(Integer id);
	
}
