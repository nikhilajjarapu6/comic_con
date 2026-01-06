package com.comiccon.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comiccon.dto.GenreRequestDto;
import com.comiccon.dto.GenreResponseDto;
import com.comiccon.entity.Genre;
import com.comiccon.exceptions.ResourceNotFoundException;
import com.comiccon.mapper.GenreMapper;
import com.comiccon.repository.GenreRepository;
import com.comiccon.service.GenreService;

import jakarta.transaction.Transactional;

@Service
public class GenreServiceImpl implements GenreService {
	
	private final GenreRepository repo;
	private final GenreMapper mapper;
	
  
	public GenreServiceImpl(GenreRepository repo, GenreMapper mapper) {
		super();
		this.repo = repo;
		this.mapper = mapper;
	}

	@Override
	@Transactional
	public GenreResponseDto addGenre(GenreRequestDto dto) {
		Genre genre = mapper.toEntity(dto);
		return mapper.toDto(repo.save(genre));
	}

	@Override
	public List<GenreResponseDto> listOfGenre() {
		return repo.findAll().stream()
			          .map(mapper::toDto)
			          .collect(Collectors.toList());
	}

	@Override
	public GenreResponseDto getGenreById(Integer id) {
		Genre genre = repo.findById(id)
				.orElseThrow(()->new ResourceNotFoundException("Genre not found")
			    		.addDetail("genreId",id));
		return mapper.toDto(genre);
	}

	@Override
	@Transactional
	public GenreResponseDto updateGenre(GenreRequestDto dto, Integer id) {
		Genre genre =repo.findById(id)
				.orElseThrow(()->new ResourceNotFoundException("Genre not found")
			    		.addDetail("genreId",id));
		mapper.updateFromDto(dto, genre);
		
		return mapper.toDto(repo.save(genre));
	}

	@Override
	public void deleteGenre(Integer id) {
		Genre genre =repo.findById(id)
				.orElseThrow(()->new ResourceNotFoundException("Genre not found")
			    		.addDetail("genreId",id));
		repo.delete(genre);
	}

}
