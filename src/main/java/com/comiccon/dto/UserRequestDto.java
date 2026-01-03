package com.comiccon.dto;

import com.comiccon.entity.Role;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class UserRequestDto {
		
	private String username;
	private String email;
	private String password;
	private Role role;
}
