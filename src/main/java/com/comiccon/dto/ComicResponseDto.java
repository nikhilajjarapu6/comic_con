package com.comiccon.dto;

import lombok.Data;

@Data
public class ComicResponseDto {
	private Integer id;
	private String title;
	private String publisher;
	private String author;
	private String description;
	private Double price;
//	private GenreResponseDto genre;
	private String genre;
}
