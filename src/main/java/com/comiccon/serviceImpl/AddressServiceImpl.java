package com.comiccon.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comiccon.dto.AddressRequestDto;
import com.comiccon.dto.AddressResponseDto;
import com.comiccon.entity.Address;
import com.comiccon.entity.User;
import com.comiccon.exceptions.ResourceNotFoundException;
import com.comiccon.mapper.AddressMapper;
import com.comiccon.repository.AddressRepository;
import com.comiccon.repository.UserRepository;
import com.comiccon.service.AddressService;

import jakarta.transaction.Transactional;

@Service
public class AddressServiceImpl implements AddressService{
	
	@Autowired
	AddressRepository repo;
	
	@Autowired
	AddressMapper mapper;
	
	@Autowired
	UserRepository repository;

	@Override
	@Transactional
	public AddressResponseDto saveAddress(AddressRequestDto dto) {
		Address address = mapper.toEntity(dto);
		User user = repository.findById(dto.getUserId())
				 .orElseThrow(()->new ResourceNotFoundException("user not found")
				    		.addDetail("User ID:",dto.getUserId()));
		
		address.setUser(user);
		return mapper.toDto(repo.save(address));
	}

	@Override
	public List<AddressResponseDto> listOfAddress() {
		return repo.findAll().stream()
				   .map(mapper::toDto)
				   .collect(Collectors.toList());
	}

	@Override
	public AddressResponseDto findAddressById(Integer id) {
		Address address = repo.findById(id)
				 .orElseThrow(()->new ResourceNotFoundException("Address not found")
				    		.addDetail("Address ID:",id));
		return mapper.toDto(address);
		
	}

	@Override
	@Transactional
	public AddressResponseDto updateAddress(AddressRequestDto dto, Integer id) {
		Address address = repo.findById(id)
				 .orElseThrow(()->new ResourceNotFoundException("Address not found")
				    		.addDetail("Address ID:",id));
		User user = repository.findById(dto.getUserId())
				 .orElseThrow(()->new ResourceNotFoundException("user not found")
				    		.addDetail("User ID:",id));
		address.setUser(user);
		mapper.updateFromDto(dto, address);
		
		return mapper.toDto(address);
	}

	@Override
	@Transactional
	public void deleteAddress(Integer id) {
		Address address = repo.findById(id)
				 .orElseThrow(()->new ResourceNotFoundException("Address not found")
				    		.addDetail("Address ID:",id));
		repo.delete(address);
		
	}
	
	
	
}
