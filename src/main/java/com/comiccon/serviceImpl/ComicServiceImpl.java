package com.comiccon.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comiccon.dto.ComicRequestDto;
import com.comiccon.dto.ComicResponseDto;
import com.comiccon.entity.Comic;
import com.comiccon.entity.Genre;
import com.comiccon.mapper.ComicMapper;
import com.comiccon.repository.ComicRepository;
import com.comiccon.repository.GenreRepository;
import com.comiccon.service.ComicService;

import jakarta.transaction.Transactional;

@Service
public class ComicServiceImpl implements ComicService {
	
	@Autowired
	ComicRepository repo;
	
	@Autowired
	GenreRepository genreRepository;
	
	@Autowired
	ComicMapper mapper;

	@Override
	@Transactional
	public ComicResponseDto saveComic(ComicRequestDto dto) {
		Comic comic = mapper.toEntity(dto);
		Genre genre = genreRepository.findById(dto.getGenreId())
			    	   .orElseThrow(()->new RuntimeException("genre not found"));
			comic.setGenre(genre);
		
		return mapper.toDto(repo.save(comic));
	}

	@Override
	public List<ComicResponseDto> listOfComics() {
		return repo.findAll().stream()
		    .map(mapper::toDto)
		    .collect(Collectors.toList());
	}

	@Override
	public Optional<ComicResponseDto> findComicById(Integer id) {
		 return repo.findById(id)
                 .map(mapper::toDto);
	}

	@Override
	@Transactional
	public ComicResponseDto updateComic(ComicRequestDto dto, Integer id) {
		Comic comic = repo.findById(id)
			.orElseThrow(()->new RuntimeException("no comic found"));
		mapper.updateFromDto(dto, comic);
		System.out.println("***********************************");
		System.out.println(dto.getGenreId());
		Genre genre = genreRepository.findById(dto.getGenreId())
                .orElseThrow(() -> new RuntimeException("Genre not found"));
		comic.setGenre(genre);
		return mapper.toDto(repo.save(comic));
	}

	@Override
	@Transactional
	public void deleteComic(Integer id) {
		Comic comic = repo.findById(id)
		.orElseThrow(()->new RuntimeException("no comic found"));
		repo.delete(comic);
		
	}

}
