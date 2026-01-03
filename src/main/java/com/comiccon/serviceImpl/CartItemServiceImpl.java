package com.comiccon.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comiccon.dto.CartItemRequestDto;
import com.comiccon.dto.CartItemResponseDto;
import com.comiccon.repository.CartItemRepository;
import com.comiccon.repository.CartRepository;
import com.comiccon.repository.ComicRepository;
import com.comiccon.repository.UserRepository;
import com.comiccon.service.CartItemService;

@Service
public class CartItemServiceImpl implements CartItemService{
	
	@Autowired
	CartItemRepository repo;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	CartItemRepository itemRepository;
	
	@Autowired
	CartRepository cartRepository;
	
	@Autowired
	ComicRepository comicRepository;

	@Override
	public CartItemResponseDto saveCartItem(CartItemRequestDto dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CartItemResponseDto> listOfCartItems() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CartItemResponseDto findCartItemById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CartItemResponseDto updateCartItem(CartItemResponseDto dto, Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteCartItem(Integer id) {
		// TODO Auto-generated method stub
		
	}
	
}		
