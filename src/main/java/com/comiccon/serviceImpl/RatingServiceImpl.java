package com.comiccon.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comiccon.dto.RatingRequestDto;
import com.comiccon.dto.RatingResponseDto;
import com.comiccon.entity.Comic;
import com.comiccon.entity.Rating;
import com.comiccon.mapper.RatingMapper;
import com.comiccon.repository.ComicRepository;
import com.comiccon.repository.RatingRepository;
import com.comiccon.service.RatingService;

import jakarta.transaction.Transactional;

@Service
public class RatingServiceImpl implements RatingService {
		
	@Autowired
	RatingRepository repo;
	
	@Autowired
	ComicRepository comicRepository;
	
	@Autowired
	RatingMapper mapper;

	@Override
	@Transactional
	public RatingResponseDto saveRatings(RatingRequestDto dto) {
		Rating rating = mapper.toEntity(dto);
		Comic comic = comicRepository.findById(dto.getComicId())
					.orElseThrow(()->new RuntimeException("comic not found"));
		rating.setComic(comic);
		return mapper.toDto(repo.save(rating));
	}

	@Override
	public List<RatingResponseDto> listOfRatings() {
		return repo.findAll()
			 .stream()
			 .map(mapper::toDto)
			 .collect(Collectors.toList());
	}

	@Override
	public RatingResponseDto findRatingById(Integer id) {
		Rating rating = repo.findById(id)
			.orElseThrow(()->new RuntimeException("rating was not found"));
		return mapper.toDto(rating);
	}

	@Override
	@Transactional
	public RatingResponseDto updateRating(RatingRequestDto dto, Integer id) {
		Rating rating = repo.findById(id)
				.orElseThrow(()->new RuntimeException("rating was not found"));
		 Comic comic = comicRepository.findById(dto.getComicId())
			.orElseThrow(()->new RuntimeException("comic not found"));
		 rating.setComic(comic);
		 mapper.updateFromDto(dto, rating);
		 
		return mapper.toDto(repo.save(rating));
	}

	@Override
	@Transactional
	public void deleteRating(Integer id) {
		Rating rating = repo.findById(id)
				.orElseThrow(()->new RuntimeException("rating was not found"));
		repo.delete(rating);
		
	}
	
	
	
}
