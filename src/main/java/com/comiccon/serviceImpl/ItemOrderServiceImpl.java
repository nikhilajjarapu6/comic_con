package com.comiccon.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comiccon.dto.ItemOrderRequestDto;
import com.comiccon.dto.ItemOrderResponseDto;
import com.comiccon.entity.Comic;
import com.comiccon.entity.OrderItem;
import com.comiccon.entity.OrderStatus;
import com.comiccon.entity.Orders;
import com.comiccon.exceptions.BadRequestException;
import com.comiccon.exceptions.InvalidOperationException;
import com.comiccon.exceptions.ResourceNotFoundException;
import com.comiccon.mapper.ItemOrderMapper;
import com.comiccon.repository.ComicRepository;
import com.comiccon.repository.GenreRepository;
import com.comiccon.repository.OrderItemRepository;
import com.comiccon.repository.OrderRepository;
import com.comiccon.service.ItemOrderService;

import jakarta.transaction.Transactional;

@Service
public class ItemOrderServiceImpl implements ItemOrderService {
	
	
	private final OrderItemRepository repo;

	private final ItemOrderMapper mapper;
	
	private final OrderRepository orderRepository;
	
	private final ComicRepository comicRepository;
	
	public ItemOrderServiceImpl(OrderItemRepository repo, ItemOrderMapper mapper, OrderRepository orderRepository,
			ComicRepository comicRepository) {
		super();
		this.repo = repo;
		this.mapper = mapper;
		this.orderRepository = orderRepository;
		this.comicRepository = comicRepository;
	}

	@Override
	@Transactional
	public ItemOrderResponseDto saveItemOrder(ItemOrderRequestDto dto) {
		
		if (dto.getQuantity() <= 0) {
		    throw new BadRequestException("Quantity must be greater than zero")
		            .addDetail("quantity", dto.getQuantity());
		}
		
		Comic comic = comicRepository.findById(dto.getComicId())
				.orElseThrow(()->new ResourceNotFoundException("Comic not found")
			    		.addDetail("comicId",dto.getComicId()));
		Orders order = orderRepository.findById(dto.getOrderid())
				.orElseThrow(()->new ResourceNotFoundException("Order not found")
			    		.addDetail("orderId",dto.getOrderid()));
		
		if (order.getOrderStatus() == OrderStatus.CANCELLED ||
			    order.getOrderStatus() == OrderStatus.DELIVERED) {

			    throw new InvalidOperationException("Cannot modify items for this order")
			            .addDetail("orderId", order.getId())
			            .addDetail("status", order.getOrderStatus());
			}

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
				.orElseThrow(()->new ResourceNotFoundException("Order item not found")
			    		.addDetail("orderItemId",id));
		return mapper.toDto(orderitem);
	}

	@Override
	@Transactional
	public ItemOrderResponseDto updateItemOrder(ItemOrderRequestDto dto, Integer id) {
		if (dto.getQuantity() <= 0) {
		    throw new BadRequestException("Quantity must be greater than zero")
		            .addDetail("quantity", dto.getQuantity());
		}

		OrderItem itemOrder = repo.findById(id)
				.orElseThrow(()->new ResourceNotFoundException("Order item not found")
			    		.addDetail("orderItemId",id));
		Comic comic = comicRepository.findById(dto.getComicId())
				.orElseThrow(()->new ResourceNotFoundException("Comic not found")
			    		.addDetail("comicId",dto.getComicId()));
		Orders order = orderRepository.findById(dto.getOrderid())
				.orElseThrow(()->new ResourceNotFoundException("Order not found")
			    		.addDetail("orderId",dto.getOrderid()));
		if (order.getOrderStatus() == OrderStatus.CANCELLED ||
			    order.getOrderStatus() == OrderStatus.DELIVERED) {

			    throw new InvalidOperationException("Cannot modify items for this order")
			            .addDetail("orderId", order.getId())
			            .addDetail("status", order.getOrderStatus());
			}

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
				.orElseThrow(()->new ResourceNotFoundException("Order item not found")
			    		.addDetail("orderItemId",id));
		if (orderitem.getOrder().getOrderStatus() != OrderStatus.CANCELLED) {
		    throw new InvalidOperationException("Cannot delete item from active order")
		            .addDetail("orderItemId", id)
		            .addDetail("orderStatus", orderitem.getOrder().getOrderStatus());
		}

		repo.delete(orderitem);
	}
	
}             
