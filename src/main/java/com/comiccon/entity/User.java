package com.comiccon.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class User {
		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String username;
	private String email;
	private String password;
	private LocalDateTime createdAt;
	private LocalDateTime lastLogin;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	//runs only at insertion
	@PrePersist
	public void onCreate() {
		this.lastLogin = LocalDateTime.now();
		this.createdAt=LocalDateTime.now();
	}
	
	//runs on every update
	@PreUpdate
	public void onUpdate() {
		this.lastLogin=LocalDateTime.now();
	}
}
