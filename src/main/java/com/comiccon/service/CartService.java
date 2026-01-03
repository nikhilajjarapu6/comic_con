package com.comiccon.service;

import java.util.List;

import com.comiccon.dto.CartRequestDto;
import com.comiccon.dto.CartResponseDto;

public interface CartService {
	CartResponseDto addcart(CartRequestDto dto);
	List<CartResponseDto> cartList();
	CartResponseDto findCartById(Integer id);
	CartResponseDto updateCart(CartRequestDto dto, Integer id);
	void deleteCart(Integer id);
}
