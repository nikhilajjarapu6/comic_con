package com.comiccon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.comiccon.entity.Stock;

public interface StockRepository extends JpaRepository<Stock,Integer> {

}
