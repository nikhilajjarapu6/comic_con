package com.comiccon.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.comiccon.dto.ComicRequestDto;
import com.comiccon.dto.ComicResponseDto;
import com.comiccon.service.ComicService;

@RestController
@RequestMapping("/api/comics")
public class ComicController {

    @Autowired
    private ComicService comicService;

    @PostMapping
    public ResponseEntity<ComicResponseDto> addComic(@RequestBody ComicRequestDto dto) {
        ComicResponseDto savedComic = comicService.saveComic(dto);
        return ResponseEntity.status(201).body(savedComic);
    }

    @GetMapping
    public ResponseEntity<List<ComicResponseDto>> getAllComics() {
        return ResponseEntity.ok(comicService.listOfComics());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComicResponseDto> getComicById(@PathVariable Integer id) {
        Optional<ComicResponseDto> comic = comicService.findComicById(id);
        return comic.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComicResponseDto> updateComic(@RequestBody ComicRequestDto dto, @PathVariable Integer id) {
        ComicResponseDto updatedComic = comicService.updateComic(dto, id);
        return ResponseEntity.ok(updatedComic);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComic(@PathVariable Integer id) {
        comicService.deleteComic(id);
        return ResponseEntity.noContent().build();
    }
}
