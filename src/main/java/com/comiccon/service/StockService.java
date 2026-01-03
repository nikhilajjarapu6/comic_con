package com.comiccon.service;

import java.util.List;

import com.comiccon.dto.StockRequestDto;
import com.comiccon.dto.StockResponseDto;

public interface StockService {
	StockResponseDto saveStock(StockRequestDto dto);
	List<StockResponseDto> listOfStock();
	StockResponseDto updateStock(StockRequestDto dto, Integer id);
	StockResponseDto findStockById(Integer id);
	void removeStock(Integer id);
}
