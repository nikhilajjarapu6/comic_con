package com.comiccon.dto;

import lombok.Data;

@Data
public class CartItemResponseDto {
	private Integer id;
	private String comicTitle;
	private Integer quantity;
	private Integer cartId;
}
