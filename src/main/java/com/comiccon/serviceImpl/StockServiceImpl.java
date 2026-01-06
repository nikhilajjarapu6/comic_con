package com.comiccon.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comiccon.dto.StockRequestDto;
import com.comiccon.dto.StockResponseDto;
import com.comiccon.entity.Comic;
import com.comiccon.entity.Stock;
import com.comiccon.exceptions.ResourceNotFoundException;
import com.comiccon.mapper.StockMapper;
import com.comiccon.repository.ComicRepository;
import com.comiccon.repository.StockRepository;
import com.comiccon.service.StockService;

import jakarta.transaction.Transactional;

@Service
public class StockServiceImpl implements StockService {
	

	private final StockRepository repo;
	private final  StockMapper mapper;
	private final ComicRepository comicRepository;

	
	public StockServiceImpl(StockRepository repo, StockMapper mapper, ComicRepository comicRepository) {
		super();
		this.repo = repo;
		this.mapper = mapper;
		this.comicRepository = comicRepository;
	}

	@Override
	@Transactional
	public StockResponseDto saveStock(StockRequestDto dto) {
		
		Comic comic = comicRepository.findById(dto.getComicId())
				.orElseThrow(()->new ResourceNotFoundException("Comic not found")
			    		.addDetail("comicId",dto.getComicId()));
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
				.orElseThrow(()->new ResourceNotFoundException("Stock not found")
			    		.addDetail("stockId",id));
		Comic comic = comicRepository.findById(dto.getComicId())
				.orElseThrow(()->new ResourceNotFoundException("Comic not found")
			    		.addDetail("comicId",dto.getComicId()));
		stock.setComic(comic);
		mapper.updateFromDto(dto, stock);
		return mapper.toDto(repo.save(stock));
	}

	@Override
	public StockResponseDto findStockById(Integer id) {
		Stock stock = repo.findById(id)
				.orElseThrow(()->new ResourceNotFoundException("Stock not found")
			    		.addDetail("stockId",id));
		return mapper.toDto(stock);
	}

	@Override
	public void removeStock(Integer id) {
		Stock stock = repo.findById(id)
				.orElseThrow(()->new ResourceNotFoundException("Stock not found")
			    		.addDetail("stockId",id));
		repo.delete(stock);
		
	}
	
	
}
