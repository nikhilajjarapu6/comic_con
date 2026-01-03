package com.comiccon.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.comiccon.dto.CartRequestDto;
import com.comiccon.dto.CartResponseDto;
import com.comiccon.service.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {
	
	  @Autowired
	    private CartService cartService;

	    @PostMapping
	    public ResponseEntity<CartResponseDto> createCart(@RequestBody CartRequestDto dto) {
	        CartResponseDto savedCart = cartService.addcart(dto);
	        return new ResponseEntity<>(savedCart, HttpStatus.CREATED);
	    }

	    @GetMapping
	    public ResponseEntity<List<CartResponseDto>> getAllCarts() {
	        return ResponseEntity.ok(cartService.cartList());
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<CartResponseDto> getCartById(@PathVariable Integer id) {
	        return ResponseEntity.ok(cartService.findCartById(id));
	    }

	    @PutMapping("/{id}")
	    public ResponseEntity<CartResponseDto> updateCart(@RequestBody CartRequestDto dto, @PathVariable Integer id) {
	        return ResponseEntity.ok(cartService.updateCart(dto, id));
	    }

	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> deleteCart(@PathVariable Integer id) {
	        cartService.deleteCart(id);
	        return ResponseEntity.noContent().build();
	    }

}
