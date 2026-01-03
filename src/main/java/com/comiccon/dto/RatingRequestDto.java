package com.comiccon.dto;

import lombok.Data;

@Data
public class RatingRequestDto {
	private Integer rating;
	private String review;
	private Integer comicId; 
}
