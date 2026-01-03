package com.comiccon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.comiccon.dto.AddressRequestDto;
import com.comiccon.dto.AddressResponseDto;
import com.comiccon.service.AddressService;

@RestController
@RequestMapping("/api/address")
public class AddressController {
	
	  @Autowired
	  private AddressService addressService;

	    @PostMapping
	    public ResponseEntity<AddressResponseDto> createAddress(@RequestBody AddressRequestDto dto) {
	        AddressResponseDto savedAddress = addressService.saveAddress(dto);
	        return new ResponseEntity<>(savedAddress, HttpStatus.CREATED);
	    }

	    @GetMapping
	    public ResponseEntity<List<AddressResponseDto>> getAllAddresses() {
	        return ResponseEntity.ok(addressService.listOfAddress());
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<AddressResponseDto> getAddressById(@PathVariable Integer id) {
	        return ResponseEntity.ok(addressService.findAddressById(id));
	    }

	    @PutMapping("/{id}")
	    public ResponseEntity<AddressResponseDto> updateAddress(@RequestBody AddressRequestDto dto, @PathVariable Integer id) {
	        AddressResponseDto updated = addressService.updateAddress(dto, id);
	        return ResponseEntity.ok(updated);
	    }

	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> deleteAddress(@PathVariable Integer id) {
	        addressService.deleteAddress(id);
	        return ResponseEntity.noContent().build();
	    }
}
