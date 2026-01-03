package com.comiccon.service;

import java.util.List;

import com.comiccon.dto.CartItemRequestDto;
import com.comiccon.dto.CartItemResponseDto;

public interface CartItemService {
	CartItemResponseDto saveCartItem(CartItemRequestDto dto);
	List<CartItemResponseDto> listOfCartItems();
	CartItemResponseDto findCartItemById(Integer id);
	CartItemResponseDto updateCartItem(CartItemResponseDto dto, Integer id);
	void deleteCartItem(Integer id);
}
