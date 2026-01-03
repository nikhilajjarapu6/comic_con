package com.comiccon.dto;

import java.util.List;

import lombok.Data;

@Data
public class CartRequestDto {
	private Integer userId;
	private List<CartItemRequestDto> items;
}
