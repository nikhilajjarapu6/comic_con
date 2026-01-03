package com.comiccon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.comiccon.dto.GenreRequestDto;
import com.comiccon.dto.GenreResponseDto;
import com.comiccon.service.GenreService;

@RestController
@RequestMapping("/api/genres")
public class GenreController {

    @Autowired
    private GenreService genreService;

    @PostMapping
    public ResponseEntity<GenreResponseDto> addGenre(@RequestBody GenreRequestDto dto) {
        GenreResponseDto savedGenre = genreService.addGenre(dto);
        return ResponseEntity.status(201).body(savedGenre);
    }

    @GetMapping
    public ResponseEntity<List<GenreResponseDto>> listGenres() {
        return ResponseEntity.ok(genreService.listOfGenre());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenreResponseDto> getGenreById(@PathVariable Integer id) {
        GenreResponseDto genre = genreService.getGenreById(id);
        return ResponseEntity.ok(genre);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenreResponseDto> updateGenre(@RequestBody GenreRequestDto dto, @PathVariable Integer id) {
        GenreResponseDto updatedGenre = genreService.updateGenre(dto, id);
        return ResponseEntity.ok(updatedGenre);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGenre(@PathVariable Integer id) {
        genreService.deleteGenre(id);
        return ResponseEntity.noContent().build();
    }
}
