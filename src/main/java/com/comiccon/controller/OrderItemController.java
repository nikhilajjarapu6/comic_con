package com.comiccon.controller;

import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.comiccon.dto.ItemOrderRequestDto;
import com.comiccon.dto.ItemOrderResponseDto;
import com.comiccon.service.ItemOrderService;

@RestController
@RequestMapping("/api/item-orders")
public class OrderItemController {
	 @Autowired
	    private ItemOrderService itemOrderService;

	    @PostMapping
	    public ResponseEntity<ItemOrderResponseDto> saveItemOrder(@RequestBody ItemOrderRequestDto dto) {
	        ItemOrderResponseDto saved = itemOrderService.saveItemOrder(dto);
	        return ResponseEntity.status(201).body(saved);
	    }

	    @GetMapping
	    public ResponseEntity<List<ItemOrderResponseDto>> getAllItemOrders() {
	        return ResponseEntity.ok(itemOrderService.listOfItemOrders());
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<ItemOrderResponseDto> getItemOrderById(@PathVariable Integer id) {
	        return ResponseEntity.ok(itemOrderService.findItemOrderById(id));
	    }

	    @PutMapping("/{id}")
	    public ResponseEntity<ItemOrderResponseDto> updateItemOrder(@RequestBody ItemOrderRequestDto dto,
	                                                                @PathVariable Integer id) {
	        return ResponseEntity.ok(itemOrderService.updateItemOrder(dto, id));
	    }

	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> deleteItemOrder(@PathVariable Integer id) {
	        itemOrderService.deleteItemOrder(id);
	        return ResponseEntity.noContent().build();
	    }
}
