package com.comiccon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.comiccon.entity.Orders;

public interface OrderRepository extends JpaRepository<Orders,Integer> {

}
