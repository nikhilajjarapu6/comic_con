package com.comiccon.dto;

import lombok.Data;

@Data
public class RatingResponseDto {
	private Integer id;
	private Integer rating;
	private String review;
	private String title;
}
