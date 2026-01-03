package com.comiccon.dto;

import lombok.Data;

@Data
public class StockRequestDto {
	private Integer quantityAvailable;
	private Integer quantitySold;
	private Integer comicId;
}
