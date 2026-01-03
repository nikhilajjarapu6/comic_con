package com.comiccon.dto;

import lombok.Data;

@Data
public class StockResponseDto {
	private Integer id;
	private Integer quantityAvailable;
	private Integer quantitySold;
	private Integer comicId;
	private String comicTitle;
	private GenreResponseDto genre;
}
