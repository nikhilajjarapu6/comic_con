package com.comiccon.dto;

import lombok.Data;

@Data
public class ItemOrderRequestDto {
	private Integer comicId;  //which comic to order
	private Integer quantity; //how many
	private Integer orderid;
}
