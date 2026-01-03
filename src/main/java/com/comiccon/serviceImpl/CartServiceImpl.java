package com.comiccon.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comiccon.dto.CartRequestDto;
import com.comiccon.dto.CartResponseDto;
import com.comiccon.entity.Cart;
import com.comiccon.entity.CartItem;
import com.comiccon.entity.Comic;
import com.comiccon.entity.User;
import com.comiccon.mapper.CartMapper;
import com.comiccon.repository.CartItemRepository;
import com.comiccon.repository.CartRepository;
import com.comiccon.repository.ComicRepository;
import com.comiccon.repository.UserRepository;
import com.comiccon.service.CartService;

import jakarta.transaction.Transactional;

@Service
public class CartServiceImpl implements CartService {
	
	@Autowired
	CartRepository repo;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	CartItemRepository itemRepository;
	
	@Autowired
	CartRepository cartRepository;
	
	@Autowired
	ComicRepository comicRepository;
	
	@Autowired
	CartMapper mapper;

	@Override
	@Transactional
	public CartResponseDto addcart(CartRequestDto dto) {
		Cart cart = mapper.toEntity(dto);
		User user = userRepository.findById(dto.getUserId())
					.orElseThrow(()->new RuntimeException("user not found"));
		
		List<CartItem> cartItems = dto.getItems().stream()
	            .map(cartItemDto -> {
	                Comic comic = comicRepository.findById(cartItemDto.getComicId())
	                        .orElseThrow(() -> new RuntimeException("comic not found"));

	                return CartItem.builder()
	                        .comic(comic)
	                        .quantity(cartItemDto.getQuantity())
	                        .cart(cart) // set parent
	                        .build();
	            }).collect(Collectors.toList());
		cart.setItems(cartItems);
		cart.setUser(user);
		
		return mapper.toDto(cartRepository.save(cart));
	}

	@Override
	public List<CartResponseDto> cartList() {
		return repo.findAll().stream()
					  .map(mapper::toDto)
					  .collect(Collectors.toList());
		
	}

	@Override
	public CartResponseDto findCartById(Integer id) {
		Cart cart = repo.findById(id)
			.orElseThrow(()->new RuntimeException("cart not found"));
		return mapper.toDto(cart);
	}

	@Transactional
	@Override
	public CartResponseDto updateCart(CartRequestDto dto, Integer id) {
	    Cart cart = repo.findById(id)
	        .orElseThrow(() -> new RuntimeException("cart not found"));

	    mapper.updateFromDto(dto, cart);

	    User user = userRepository.findById(dto.getUserId())
	        .orElseThrow(() -> new RuntimeException("user not found"));
	    cart.setUser(user);

	    // Modify existing list, don't replace
	    List<CartItem> existingItems = cart.getItems();
	    existingItems.clear();

	    List<CartItem> newItems = dto.getItems().stream().map(cartItemDto -> {
	        Comic comic = comicRepository.findById(cartItemDto.getComicId())
	            .orElseThrow(() -> new RuntimeException("comic not found"));

	        return CartItem.builder()
	            .comic(comic)
	            .quantity(cartItemDto.getQuantity())
	            .cart(cart)
	            .build();
	    }).collect(Collectors.toList());

	    existingItems.addAll(newItems);

	    return mapper.toDto(repo.save(cart));
	}



	@Override
	@Transactional
	public void deleteCart(Integer id) {
		Cart cart = repo.findById(id)
				.orElseThrow(()->new RuntimeException("cart not found"));
		repo.delete(cart);
		
	}
	
	
	
	
}
