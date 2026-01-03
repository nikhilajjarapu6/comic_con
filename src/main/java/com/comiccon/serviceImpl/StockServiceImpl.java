package com.comiccon.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comiccon.dto.StockRequestDto;
import com.comiccon.dto.StockResponseDto;
import com.comiccon.entity.Comic;
import com.comiccon.entity.Stock;
import com.comiccon.mapper.StockMapper;
import com.comiccon.repository.ComicRepository;
import com.comiccon.repository.StockRepository;
import com.comiccon.service.StockService;

import jakarta.transaction.Transactional;

@Service
public class StockServiceImpl implements StockService {
	
	@Autowired
	StockRepository repo;
	
	@Autowired
	StockMapper mapper;
	
	@Autowired
	ComicRepository comicRepository;

	@Override
	@Transactional
	public StockResponseDto saveStock(StockRequestDto dto) {
		
		Comic comic = comicRepository.findById(dto.getComicId())
					   .orElseThrow(()->new RuntimeException("stock not found"));
		System.out.println(comic.getTitle());
		Stock stock = mapper.toEntity(dto);
		stock.setComic(comic);
		return mapper.toDto(repo.save(stock));
	}

	@Override
	public List<StockResponseDto> listOfStock() {
		return repo.findAll().stream()
					.map(mapper::toDto)
					.collect(Collectors.toList());
	}

	@Override
	@Transactional
	public StockResponseDto updateStock(StockRequestDto dto, Integer id) {
		Stock stock = repo.findById(id)
			.orElseThrow(()->new RuntimeException("stock not found"));
		Comic comic = comicRepository.findById(dto.getComicId())
				   .orElseThrow(()->new RuntimeException("stock not found"));
		stock.setComic(comic);
		mapper.updateFromDto(dto, stock);
		return mapper.toDto(repo.save(stock));
	}

	@Override
	public StockResponseDto findStockById(Integer id) {
		Stock stock = repo.findById(id)
				.orElseThrow(()->new RuntimeException("stock not found"));
		return mapper.toDto(stock);
	}

	@Override
	public void removeStock(Integer id) {
		Stock stock = repo.findById(id)
				.orElseThrow(()->new RuntimeException("stock not found"));
		repo.delete(stock);
		
	}
	
	
}
