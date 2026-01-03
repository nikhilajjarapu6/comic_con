package com.comiccon.dto;

import java.util.List;

import lombok.Data;

@Data
public class CartResponseDto {
	private Integer id;
	private String username;
	private List<CartItemResponseDto> items;
	
}
