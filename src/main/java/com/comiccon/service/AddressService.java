package com.comiccon.service;

import java.util.List;

import com.comiccon.dto.AddressRequestDto;
import com.comiccon.dto.AddressResponseDto;

public interface AddressService {
	AddressResponseDto saveAddress(AddressRequestDto dto);
	List<AddressResponseDto> listOfAddress();
	AddressResponseDto findAddressById(Integer id);
	AddressResponseDto updateAddress(AddressRequestDto dto,Integer id);
	void deleteAddress(Integer id);
}
