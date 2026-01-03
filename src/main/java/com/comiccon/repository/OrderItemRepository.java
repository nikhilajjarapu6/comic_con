package com.comiccon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.comiccon.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem,Integer> {

}
