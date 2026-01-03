package com.comiccon.dto;

import lombok.Data;

@Data
public class AddressRequestDto {
	private String fullname;
	private String phoneNumber;
	private String street;
	private String landmark;
	private String postalCode;
	private String city;
	private String state;
	private Integer userId;
}
