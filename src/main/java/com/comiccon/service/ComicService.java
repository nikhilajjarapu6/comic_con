package com.comiccon.service;

import java.util.List;
import java.util.Optional;

import com.comiccon.dto.ComicRequestDto;
import com.comiccon.dto.ComicResponseDto;

public interface ComicService {
	
	ComicResponseDto saveComic(ComicRequestDto dto);
	List<ComicResponseDto> listOfComics();
	Optional<ComicResponseDto> findComicById(Integer id);
	ComicResponseDto updateComic(ComicRequestDto dto, Integer id);
	void deleteComic(Integer id);
}
