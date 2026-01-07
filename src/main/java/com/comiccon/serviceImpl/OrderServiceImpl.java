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
import com.comiccon.exceptions.BadRequestException;
import com.comiccon.exceptions.InvalidOperationException;
import com.comiccon.exceptions.ResourceNotFoundException;
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
	
	private final OrderRepository repo;

	private final UserRepository userRepository;

	private final OrderItemRepository itemRepository;

	private final AddressRepository addressRepository;

	private final ComicRepository comicRepository;

	private final OrderMapper mapper;
	
	
	
	public OrderServiceImpl(OrderRepository repo, UserRepository userRepository, OrderItemRepository itemRepository,
			AddressRepository addressRepository, ComicRepository comicRepository, OrderMapper mapper) {
		super();
		this.repo = repo;
		this.userRepository = userRepository;
		this.itemRepository = itemRepository;
		this.addressRepository = addressRepository;
		this.comicRepository = comicRepository;
		this.mapper = mapper;
	}
	@Override
	@Transactional
	public OrderResponseDto placeOrder(OrderRequestDto dto) {
		User user = userRepository.findById(dto.getUserId())
				 .orElseThrow(()->new ResourceNotFoundException("user not found")
				    		.addDetail("userId",dto.getUserId()));
		Address address = addressRepository.findById(dto.getAddressId())
				 .orElseThrow(()->new ResourceNotFoundException("Address not found")
				    		.addDetail("addressId",dto.getAddressId()));
		if (dto.getItems() == null || dto.getItems().isEmpty()) {
		    throw new BadRequestException("Order must contain at least one item");
		}

		//request DTO have list of order item 
		//before saving order list of items should be saved
		//stream gives individual itemOrder request DTO
		List<OrderItem> items = dto.getItems().stream().map(itemOrderDto->{
			//retrieve comic book based id and initialize into item Order
			Comic comic = comicRepository.findById(itemOrderDto.getComicId())
					 .orElseThrow(()->new ResourceNotFoundException("Comic not found")
					    		.addDetail("comicId",itemOrderDto.getComicId()));
			if (itemOrderDto.getQuantity() <= 0) {
			    throw new BadRequestException("Quantity must be greater than zero")
			            .addDetail("comicId", itemOrderDto.getComicId())
			            .addDetail("quantity", itemOrderDto.getQuantity());
			}

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
				 .orElseThrow(()->new ResourceNotFoundException("Order not found")
				    		.addDetail("orderId",id));
		return mapper.toDto(order);
	}

	@Override
	@Transactional
	public OrderResponseDto updateOrder(OrderRequestDto dto, Integer id) {

	    Orders existingOrder = repo.findById(id)
	    		.orElseThrow(()->new ResourceNotFoundException("Order not found")
			    		.addDetail("orderId",id));
	    
	    if (existingOrder.getOrderStatus() == OrderStatus.CANCELLED ||
	    	    existingOrder.getOrderStatus() == OrderStatus.DELIVERED) {

	    	    throw new InvalidOperationException("Order cannot be modified in current state")
	    	            .addDetail("orderId", existingOrder.getId())
	    	            .addDetail("status", existingOrder.getOrderStatus());
	    	}



	    Address address = addressRepository.findById(dto.getAddressId())
	    		.orElseThrow(()->new ResourceNotFoundException("Address not found")
			    		.addDetail("addressId",dto.getAddressId()));

	    // Batch fetch comics
	    List<Integer> comicIds = dto.getItems().stream()
	            .map(ItemOrderRequestDto::getComicId)
	            .toList();

	    Map<Integer, Comic> comicMap = comicRepository.findAllById(comicIds)
	            .stream()
	            .collect(Collectors.toMap(Comic::getId, Function.identity()));

	    List<OrderItem> existingItems = existingOrder.getItems();
	    existingItems.clear();
//	    List<OrderItem> items = new ArrayList<>();
	    double total = 0;

	    for (ItemOrderRequestDto itemDto : dto.getItems()) {

	        Comic comic = comicMap.get(itemDto.getComicId());
	        if (comic == null) {
	            throw new ResourceNotFoundException("comic not found")
	            .addDetail("comicId", itemDto.getComicId());
	        }
	        if (itemDto.getQuantity() <= 0) {
	            throw new BadRequestException("Quantity must be greater than zero")
	                    .addDetail("comicId", itemDto.getComicId())
	                    .addDetail("quantity", itemDto.getQuantity());
	        }

	        double price = comic.getPrice() * itemDto.getQuantity();
	        total += price;

	        OrderItem item = OrderItem.builder()
	                .comic(comic)
	                .quantity(itemDto.getQuantity())
	                .price(price)
	                .order(existingOrder)
	                .build();

	        existingItems.add(item);
	    }

	    existingOrder.setAddress(address);
	    existingOrder.setItems(existingItems);
	    existingOrder.setTotalAmount(total);
	    existingOrder.setOrderStatus(OrderStatus.CONFIRMED);
	    // existingOrder.setUpdatedAt(LocalDateTime.now()); // better

	    return mapper.toDto(repo.save(existingOrder));
	}


	@Override
	@Transactional
	public void deleteOrder(Integer id) {
		Orders order = repo.findById(id)
				.orElseThrow(()->new ResourceNotFoundException("Order not found")
			    		.addDetail("orderId",id));
		if (order.getOrderStatus() != OrderStatus.CANCELLED) {
		    throw new InvalidOperationException("Only cancelled orders can be deleted")
		            .addDetail("orderId", order.getId())
		            .addDetail("status", order.getOrderStatus());
		}

		repo.delete(order);
		
	}
	
}
