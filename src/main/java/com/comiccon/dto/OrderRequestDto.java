package com.comiccon.dto;

import java.util.List;

import lombok.Data;

@Data
public class OrderRequestDto {
	
	//after adding jwt user id can be removed
	private Integer userId;
	private Integer addressId;
	private List<ItemOrderRequestDto> items;
}
