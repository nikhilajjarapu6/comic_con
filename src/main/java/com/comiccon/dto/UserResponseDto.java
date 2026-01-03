package com.comiccon.dto;

import java.time.LocalDateTime;

import com.comiccon.entity.Role;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class UserResponseDto {
	
	private Integer id;
	private String username;
	private String email;
	private Role role;
	private LocalDateTime createdAt;
	private LocalDateTime lastLogin;
}
