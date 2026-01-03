package com.comiccon.dto;

import lombok.Data;

@Data
public class CartItemRequestDto {
	private Integer comicId;
//	private Integer cartId;
	private Integer quantity;
}
