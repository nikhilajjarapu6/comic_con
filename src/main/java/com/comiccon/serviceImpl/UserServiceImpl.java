package com.comiccon.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.comiccon.dto.UserRequestDto;
import com.comiccon.dto.UserResponseDto;
import com.comiccon.entity.User;
import com.comiccon.mapper.UserMapper;
import com.comiccon.repository.UserRepository;
import com.comiccon.service.UserService;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {
	
	private final UserRepository repo;
    private final UserMapper mapper;
	

	public UserServiceImpl(UserRepository repo, UserMapper mapper) {
		super();
		this.repo = repo;
		this.mapper = mapper;
	}

	@Override
	@Transactional
	public UserResponseDto registerUser(UserRequestDto dto) {
		User user = mapper.toEntity(dto);
		
		if(repo.findByEmail(user.getEmail())!=null) {
			throw new RuntimeException("Email is alredy exists");
		}
//		user.setLastLogin(LocalDateTime.now());
		return mapper.toDto(repo.save(user));
	}

	@Override
	public UserResponseDto getById(Integer id) {
		 User user = repo.findById(id)
		    .orElseThrow(()->new RuntimeException("user not found with id "+id));
		 return mapper.toDto(user);
	}

	@Override
	public List<UserResponseDto> listOfUsers() {
		 return repo.findAll().stream()
				 	.map(mapper::toDto)
				 	.collect(Collectors.toList());
	}

	@Override
	@Transactional
	public UserResponseDto updateUser(UserRequestDto dto, Integer id) {
		
		User fetchedUser = repo.findById(id)
				.orElseThrow(()->new RuntimeException("user not found with id "+id));
		
		//automatically updates each field
		mapper.updateFromDto(dto, fetchedUser);
//		fetchedUser.setEmail(dto.getEmail());
//		fetchedUser.setPassword(dto.getPassword());
//		fetchedUser.setUsername(dto.getUsername());
//		fetchedUser.setRole(dto.getRole());
		
		return mapper.toDto(repo.save(fetchedUser));
	}

	@Override
	@Transactional
	public void deleteUser(Integer id) {
		User user = repo.findById(id)
	    	.orElseThrow(()->new RuntimeException("user not found with id "+id));	
		repo.delete(user);
	}
}
