package com.comiccon.service;

import java.util.List;

import com.comiccon.dto.RatingRequestDto;
import com.comiccon.dto.RatingResponseDto;

public interface RatingService {
	RatingResponseDto saveRatings(RatingRequestDto dto);
	List<RatingResponseDto> listOfRatings();
	RatingResponseDto findRatingById(Integer id);
	RatingResponseDto updateRating(RatingRequestDto dto,Integer id);
	void deleteRating(Integer id);
}
