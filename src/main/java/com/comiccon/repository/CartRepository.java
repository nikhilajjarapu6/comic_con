package com.comiccon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.comiccon.entity.Cart;

public interface CartRepository extends JpaRepository<Cart,Integer> {

}
