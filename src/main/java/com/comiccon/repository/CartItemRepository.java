package com.comiccon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.comiccon.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

}
