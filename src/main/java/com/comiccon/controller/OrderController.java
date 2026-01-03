package com.comiccon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.comiccon.dto.OrderRequestDto;
import com.comiccon.dto.OrderResponseDto;
import com.comiccon.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController { 

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDto> placeOrder(@RequestBody OrderRequestDto dto) {
        OrderResponseDto response = orderService.placeOrder(dto);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getAllOrders() {
        return ResponseEntity.ok(orderService.listOfOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable Integer id) {
        return ResponseEntity.ok(orderService.findOrderById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponseDto> updateOrder(
            @RequestBody OrderRequestDto dto, @PathVariable Integer id) {
        return ResponseEntity.ok(orderService.updateOrder(dto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Integer id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
