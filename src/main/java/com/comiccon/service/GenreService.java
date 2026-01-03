package com.comiccon.service;

import java.util.List;

import com.comiccon.dto.GenreRequestDto;
import com.comiccon.dto.GenreResponseDto;

public interface GenreService {
	
	GenreResponseDto addGenre(GenreRequestDto dto);
	List<GenreResponseDto> listOfGenre();
	GenreResponseDto getGenreById(Integer id);
	GenreResponseDto updateGenre(GenreRequestDto dto,Integer id);
	void deleteGenre(Integer id);
	
}
