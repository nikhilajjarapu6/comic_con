package com.comiccon.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comiccon.dto.RatingRequestDto;
import com.comiccon.dto.RatingResponseDto;
import com.comiccon.entity.Comic;
import com.comiccon.entity.Rating;
import com.comiccon.exceptions.ResourceNotFoundException;
import com.comiccon.mapper.RatingMapper;
import com.comiccon.repository.ComicRepository;
import com.comiccon.repository.RatingRepository;
import com.comiccon.service.RatingService;

import jakarta.transaction.Transactional;

@Service
public class RatingServiceImpl implements RatingService {
		

	private final RatingRepository repo;
	private final ComicRepository comicRepository;
	private final RatingMapper mapper;
	
	

	public RatingServiceImpl(RatingRepository repo, ComicRepository comicRepository, RatingMapper mapper) {
		super();
		this.repo = repo;
		this.comicRepository = comicRepository;
		this.mapper = mapper;
	}

	@Override
	@Transactional
	public RatingResponseDto saveRatings(RatingRequestDto dto) {
		Rating rating = mapper.toEntity(dto);
		Comic comic = comicRepository.findById(dto.getComicId())
				.orElseThrow(()->new ResourceNotFoundException("Comic not found")
			    		.addDetail("comicId",dto.getComicId()));
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
				.orElseThrow(()->new ResourceNotFoundException("Ratings not found")
			    		.addDetail("ratingsId",id));
		return mapper.toDto(rating);
	}

	@Override
	@Transactional
	public RatingResponseDto updateRating(RatingRequestDto dto, Integer id) {
		Rating rating = repo.findById(id)
				.orElseThrow(()->new ResourceNotFoundException("Ratings not found")
			    		.addDetail("ratingsId",id));
		 Comic comic = comicRepository.findById(dto.getComicId())
				 .orElseThrow(()->new ResourceNotFoundException("Comic not found")
 			    		.addDetail("comicId",dto.getComicId()));
		 rating.setComic(comic);
		 mapper.updateFromDto(dto, rating);
		 
		return mapper.toDto(repo.save(rating));
	}

	@Override
	@Transactional
	public void deleteRating(Integer id) {
		Rating rating = repo.findById(id)
				.orElseThrow(()->new ResourceNotFoundException("Ratings not found")
			    		.addDetail("ratingsId",id));
		repo.delete(rating);
		
	}
	
	
	
}
