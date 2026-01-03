package com.comiccon.serviceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comiccon.dto.ItemOrderRequestDto;
import com.comiccon.dto.OrderRequestDto;
import com.comiccon.dto.OrderResponseDto;
import com.comiccon.entity.Address;
import com.comiccon.entity.Comic;
import com.comiccon.entity.OrderItem;
import com.comiccon.entity.OrderStatus;
import com.comiccon.entity.Orders;
import com.comiccon.entity.User;
import com.comiccon.mapper.OrderMapper;
import com.comiccon.repository.AddressRepository;
import com.comiccon.repository.ComicRepository;
import com.comiccon.repository.OrderItemRepository;
import com.comiccon.repository.OrderRepository;
import com.comiccon.repository.UserRepository;
import com.comiccon.service.OrderService;

import jakarta.transaction.Transactional;

@Service 
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	OrderRepository repo;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	OrderItemRepository itemRepository;
	
	@Autowired
	AddressRepository addressRepository;
	
	@Autowired
	ComicRepository comicRepository;
	
	@Autowired
	OrderMapper mapper;
	
	
	
	

	@Override
	@Transactional
	public OrderResponseDto placeOrder(OrderRequestDto dto) {
		User user = userRepository.findById(dto.getUserId())
			 		.orElseThrow(()->new RuntimeException("user not found"));
		Address address = addressRepository.findById(dto.getAddressId())
						.orElseThrow(()->new RuntimeException("address not found"));
		//request DTO have list of order item 
		//before saving order list of items should be saved
		//stream gives individual itemOrder request DTO
		List<OrderItem> items = dto.getItems().stream().map(itemOrderDto->{
			//retrieve comic book based id and initialize into item Order
			Comic comic = comicRepository.findById(itemOrderDto.getComicId())
						   .orElseThrow(()->new RuntimeException("comic not found"));
			return OrderItem.builder()
						    .comic(comic)
						    .quantity(itemOrderDto.getQuantity())
						    .price(comic.getPrice()*itemOrderDto.getQuantity())
						    .build();
			
		}).collect(Collectors.toList());
		
		//count total amount
		//calculates each item total and adds to total amount in order
		double totalAmount = 0.0;

		for (OrderItem item : items) {
		    totalAmount += item.getPrice();
		}

		
//		Double totalAmount=items.stream().mapToDouble(OrderItem::getPrice).sum();
		
		//now build order object to save into the order item and placing order
		Orders order = Orders.builder()
			 .user(user)
			 .address(address)
			 .items(items)
			 .orderDate(LocalDateTime.now())
			 .orderStatus(OrderStatus.CONFIRMED)
			 .totalAmount(totalAmount)
			 .build();
		
		items.forEach(item->item.setOrder(order));  //because it is bi directional
		
		Orders savedOrder = repo.save(order);
		
		return mapper.toDto(savedOrder);
		
		
	}

	@Override
	public List<OrderResponseDto> listOfOrders() {
		return repo.findAll().stream()
				   .map(mapper::toDto)
				   .toList();
	}

	@Override
	public OrderResponseDto findOrderById(Integer id) {
		Orders order = repo.findById(id)
			.orElseThrow(()->new RuntimeException("order not found"));
		return mapper.toDto(order);
	}

	@Override
	@Transactional
	public OrderResponseDto updateOrder(OrderRequestDto dto, Integer id) {

	    Orders existingOrder = repo.findById(id)
	            .orElseThrow(() -> new RuntimeException("order not found"));

	    // ❌ DO NOT update user from DTO
	    // User comes from JWT or remains unchanged

	    Address address = addressRepository.findById(dto.getAddressId())
	            .orElseThrow(() -> new RuntimeException("address not found"));

	    // Batch fetch comics
	    List<Integer> comicIds = dto.getItems().stream()
	            .map(ItemOrderRequestDto::getComicId)
	            .toList();

	    Map<Integer, Comic> comicMap = comicRepository.findAllById(comicIds)
	            .stream()
	            .collect(Collectors.toMap(Comic::getId, Function.identity()));

	    List<OrderItem> items = new ArrayList<>();
	    double total = 0;

	    for (ItemOrderRequestDto itemDto : dto.getItems()) {

	        Comic comic = comicMap.get(itemDto.getComicId());
	        if (comic == null) {
	            throw new RuntimeException("comic not found");
	        }

	        double price = comic.getPrice() * itemDto.getQuantity();
	        total += price;

	        OrderItem item = OrderItem.builder()
	                .comic(comic)
	                .quantity(itemDto.getQuantity())
	                .price(price)
	                .order(existingOrder)
	                .build();

	        items.add(item);
	    }

	    existingOrder.setAddress(address);
	    existingOrder.setItems(items);
	    existingOrder.setTotalAmount(total);
	    existingOrder.setOrderStatus(OrderStatus.CONFIRMED);
	    // existingOrder.setUpdatedAt(LocalDateTime.now()); // better

	    return mapper.toDto(repo.save(existingOrder));
	}


	@Override
	@Transactional
	public void deleteOrder(Integer id) {
		Orders order = repo.findById(id)
				.orElseThrow(()->new RuntimeException("order not found"));
		repo.delete(order);
		
	}
	
}
