package com.comiccon.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.comiccon.dto.UserRequestDto;
import com.comiccon.dto.UserResponseDto;
import com.comiccon.entity.User;
import com.comiccon.exceptions.ResourceAlreadyExistsException;
import com.comiccon.exceptions.ResourceNotFoundException;
import com.comiccon.mapper.UserMapper;
import com.comiccon.repository.UserRepository;
import com.comiccon.service.UserService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {
	
	private final UserRepository repo;
    private final UserMapper mapper;
    private final BCryptPasswordEncoder encoder;
	

	public UserServiceImpl(UserRepository repo, UserMapper mapper, BCryptPasswordEncoder encoder) {
		super();
		this.repo = repo;
		this.mapper = mapper;
		this.encoder=encoder;
	}

	@Override
	@Transactional
	public UserResponseDto registerUser(UserRequestDto dto) {
		User user = mapper.toEntity(dto);
		user.setPassword(encoder.encode(dto.getPassword()));
		if(repo.findByEmail(user.getEmail())!=null) {
			throw new ResourceAlreadyExistsException("Email already exists")
                   .addDetail("EmailId", dto.getEmail());
		}
//		user.setLastLogin(LocalDateTime.now());
		return mapper.toDto(repo.save(user));
	}

	@Override
	public UserResponseDto getById(Integer id) {
		 User user = repo.findById(id)
		    .orElseThrow(()->new ResourceNotFoundException("user not found")
		    		.addDetail("User ID:",id));
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
				 .orElseThrow(()->new ResourceNotFoundException("user not found")
				    		.addDetail("User ID:",id));
		fetchedUser.setPassword(encoder.encode(dto.getPassword()));
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
				 .orElseThrow(()->new ResourceNotFoundException("user not found")
				    		.addDetail("User ID:",id));
		repo.delete(user);
	}
}
