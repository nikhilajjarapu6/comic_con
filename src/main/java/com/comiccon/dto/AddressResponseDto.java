package com.comiccon.dto;

import lombok.Data;

@Data
public class AddressResponseDto {
	private Integer id;
	private String fullname;
	private String phoneNumber;
	private String street;
	private String landmark;
	private String postalCode;
	private String city;
	private String state;
	private String username;
}
