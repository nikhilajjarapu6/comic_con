package com.comiccon.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comiccon.dto.CartItemRequestDto;
import com.comiccon.dto.CartItemResponseDto;
import com.comiccon.entity.Cart;
import com.comiccon.entity.CartItem;
import com.comiccon.entity.Comic;
import com.comiccon.exceptions.ResourceNotFoundException;
import com.comiccon.mapper.CartItemMapper;
import com.comiccon.repository.CartItemRepository;
import com.comiccon.repository.CartRepository;
import com.comiccon.repository.ComicRepository;
import com.comiccon.repository.UserRepository;
import com.comiccon.service.CartItemService;

@Service
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final ComicRepository comicRepository;
    private final CartItemMapper mapper;

    public CartItemServiceImpl(
            CartItemRepository cartItemRepository,
            CartRepository cartRepository,
            ComicRepository comicRepository,
            CartItemMapper mapper) {

        this.cartItemRepository = cartItemRepository;
        this.cartRepository = cartRepository;
        this.comicRepository = comicRepository;
        this.mapper = mapper;
    }

    @Override
    public CartItemResponseDto saveCartItem(CartItemRequestDto dto) {

        Cart cart = cartRepository.findById(dto.getCartId())
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found")
                        .addDetail("cartId", dto.getCartId()));

        Comic comic = comicRepository.findById(dto.getComicId())
                .orElseThrow(() -> new ResourceNotFoundException("Comic not found")
                        .addDetail("comicId", dto.getComicId()));

        CartItem cartItem = mapper.toEntity(dto);
        cartItem.setCart(cart);
        cartItem.setComic(comic);

        return mapper.toDto(cartItemRepository.save(cartItem));
    }

    @Override
    public List<CartItemResponseDto> listOfCartItems() {
        return cartItemRepository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public CartItemResponseDto findCartItemById(Integer id) {
        CartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found")
                        .addDetail("cartItemId", id));

        return mapper.toDto(cartItem);
    }

 

    @Override
    public void deleteCartItem(Integer id) {
        CartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found")
                        .addDetail("cartItemId", id));

        cartItemRepository.delete(cartItem);
    }

	@Override
	public CartItemResponseDto updateCartItem(CartItemRequestDto dto, Integer id) {
		CartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found")
                        .addDetail("cartItemId", id));

        Cart cart = cartRepository.findById(dto.getCartId())
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found")
                        .addDetail("cartId", dto.getCartId()));

        Comic comic = comicRepository.findById(dto.getComicId())
                .orElseThrow(() -> new ResourceNotFoundException("Comic not found")
                        .addDetail("comicId", dto.getComicId()));

        mapper.updateFromDto(dto, cartItem);
        cartItem.setCart(cart);
        cartItem.setComic(comic);

        return mapper.toDto(cartItemRepository.save(cartItem));
	}

}
