package com.comiccon.dto;

import lombok.Data;

@Data
public class ItemOrderResponseDto {
	private Integer id;
//	private ComicResponseDto comic;
	private String title;
	private String genre;
	private String author;
	private Integer quantity;
	private Double price;
}
