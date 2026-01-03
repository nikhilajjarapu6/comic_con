package com.comiccon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.comiccon.dto.StockRequestDto;
import com.comiccon.dto.StockResponseDto;
import com.comiccon.service.StockService;

@RestController
@RequestMapping("/api/stocks")
public class StockController {

    @Autowired
    private StockService stockService;

    @PostMapping
    public ResponseEntity<StockResponseDto> saveStock(@RequestBody StockRequestDto dto) {
        return ResponseEntity.ok(stockService.saveStock(dto));
    }

    @GetMapping
    public ResponseEntity<List<StockResponseDto>> listAllStocks() {
        return ResponseEntity.ok(stockService.listOfStock());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockResponseDto> findStockById(@PathVariable Integer id) {
        return ResponseEntity.ok(stockService.findStockById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StockResponseDto> updateStock(@RequestBody StockRequestDto dto, @PathVariable Integer id) {
        return ResponseEntity.ok(stockService.updateStock(dto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable Integer id) {
        stockService.removeStock(id);
        return ResponseEntity.noContent().build();
    }
}
