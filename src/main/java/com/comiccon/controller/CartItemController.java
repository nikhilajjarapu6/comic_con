package com.comiccon.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.comiccon.dto.CartItemRequestDto;
import com.comiccon.dto.CartItemResponseDto;
import com.comiccon.service.CartItemService;

@RestController
@RequestMapping("/api/cart-items")
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    @PostMapping
    public ResponseEntity<CartItemResponseDto> addCartItem(@RequestBody CartItemRequestDto dto) {
        CartItemResponseDto savedItem = cartItemService.saveCartItem(dto);
        return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CartItemResponseDto>> getAllCartItems() {
        return ResponseEntity.ok(cartItemService.listOfCartItems());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartItemResponseDto> getCartItemById(@PathVariable Integer id) {
        return ResponseEntity.ok(cartItemService.findCartItemById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartItemResponseDto> updateCartItem(@RequestBody CartItemResponseDto dto, @PathVariable Integer id) {
        return ResponseEntity.ok(cartItemService.updateCartItem(dto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable Integer id) {
        cartItemService.deleteCartItem(id);
        return ResponseEntity.noContent().build();
    }
}

