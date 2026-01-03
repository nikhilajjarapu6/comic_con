package com.comiccon.service;

import java.util.List;

import com.comiccon.dto.ItemOrderRequestDto;
import com.comiccon.dto.ItemOrderResponseDto;

public interface ItemOrderService {
	ItemOrderResponseDto saveItemOrder(ItemOrderRequestDto dto);
	List<ItemOrderResponseDto> listOfItemOrders();
	ItemOrderResponseDto findItemOrderById(Integer id);
	ItemOrderResponseDto updateItemOrder(ItemOrderRequestDto dto,Integer id);
	void deleteItemOrder(Integer id);
}
