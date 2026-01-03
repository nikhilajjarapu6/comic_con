package com.comiccon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.comiccon.dto.RatingRequestDto;
import com.comiccon.dto.RatingResponseDto;
import com.comiccon.service.RatingService;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @PostMapping
    public ResponseEntity<RatingResponseDto> saveRating(@RequestBody RatingRequestDto dto) {
        return ResponseEntity.ok(ratingService.saveRatings(dto));
    }

    @GetMapping
    public ResponseEntity<List<RatingResponseDto>> listRatings() {
        return ResponseEntity.ok(ratingService.listOfRatings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RatingResponseDto> getRatingById(@PathVariable Integer id) {
        return ResponseEntity.ok(ratingService.findRatingById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RatingResponseDto> updateRating(@RequestBody RatingRequestDto dto, @PathVariable Integer id) {
        return ResponseEntity.ok(ratingService.updateRating(dto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRating(@PathVariable Integer id) {
        ratingService.deleteRating(id);
        return ResponseEntity.noContent().build();
    }
}
