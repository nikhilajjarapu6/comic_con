package com.comiccon.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comiccon.dto.ItemOrderRequestDto;
import com.comiccon.dto.ItemOrderResponseDto;
import com.comiccon.entity.Comic;
import com.comiccon.entity.OrderItem;
import com.comiccon.entity.Orders;
import com.comiccon.mapper.ItemOrderMapper;
import com.comiccon.repository.ComicRepository;
import com.comiccon.repository.GenreRepository;
import com.comiccon.repository.OrderItemRepository;
import com.comiccon.repository.OrderRepository;
import com.comiccon.service.ItemOrderService;

import jakarta.transaction.Transactional;

@Service
public class ItemOrderServiceImpl implements ItemOrderService {
	
	@Autowired
	OrderItemRepository repo;
	
	@Autowired
	ItemOrderMapper mapper;
	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	ComicRepository comicRepository;
	
	@Autowired
	GenreRepository genreRepository;

	@Override
	@Transactional
	public ItemOrderResponseDto saveItemOrder(ItemOrderRequestDto dto) {
		
		Comic comic = comicRepository.findById(dto.getComicId())
			           .orElseThrow(()->new RuntimeException("comic was not found"));
		Orders order = orderRepository.findById(dto.getOrderid())
					   .orElseThrow(()->new RuntimeException("order not found"));
		OrderItem itemOrder = mapper.toEntity(dto);
		itemOrder.setComic(comic);
		itemOrder.setOrder(order);
		itemOrder.setPrice(comic.getPrice()*dto.getQuantity());
		return mapper.toDto(repo.save(itemOrder));
	}

	@Override
	public List<ItemOrderResponseDto> listOfItemOrders() {
	  return	repo.findAll().stream()
					.map(mapper::toDto)
					.collect(Collectors.toList());
	}

	@Override
	public ItemOrderResponseDto findItemOrderById(Integer id) {
		OrderItem orderitem = repo.findById(id)
			.orElseThrow(()->new RuntimeException("item order was not found"));
		return mapper.toDto(orderitem);
	}

	@Override
	@Transactional
	public ItemOrderResponseDto updateItemOrder(ItemOrderRequestDto dto, Integer id) {
		OrderItem itemOrder = repo.findById(id)
					.orElseThrow(() -> new RuntimeException("item order was not found"));
		Comic comic = comicRepository.findById(dto.getComicId())
				    .orElseThrow(() -> new RuntimeException("comic was not found"));
		Orders order = orderRepository.findById(dto.getOrderid())
				    .orElseThrow(() -> new RuntimeException("order not found"));
		itemOrder.setComic(comic);
		itemOrder.setOrder(order);
		itemOrder.setPrice(comic.getPrice()*dto.getQuantity());
		mapper.updateFromDto(dto, itemOrder);
		return mapper.toDto(repo.save(itemOrder));
	}

	@Override
	@Transactional
	public void deleteItemOrder(Integer id) {
		OrderItem orderitem = repo.findById(id)
				.orElseThrow(()->new RuntimeException("item order was not found"));
		repo.delete(orderitem);
	}
	
}             
