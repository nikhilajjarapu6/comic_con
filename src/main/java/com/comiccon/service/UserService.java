package com.comiccon.service;

import java.util.List;
import com.comiccon.dto.UserRequestDto;
import com.comiccon.dto.UserResponseDto;

public interface UserService {
	
	UserResponseDto registerUser(UserRequestDto user);
	UserResponseDto getById(Integer id);
	List<UserResponseDto> listOfUsers();
	UserResponseDto updateUser(UserRequestDto user,Integer id);
	void deleteUser(Integer id);
	
	
}
