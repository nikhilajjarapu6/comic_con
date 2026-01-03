package com.comiccon.dto;

import lombok.Data;

@Data
public class ComicRequestDto {
	private String title;
	private String publisher;
	private String author;
	private String description;
	private Double price;
	private Integer genreId;
}
