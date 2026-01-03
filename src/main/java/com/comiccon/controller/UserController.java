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

import com.comiccon.dto.UserRequestDto;
import com.comiccon.dto.UserResponseDto;
import com.comiccon.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	UserService service;

	@PostMapping
	public ResponseEntity<UserResponseDto> saveUser(@RequestBody UserRequestDto dto){
		UserResponseDto user = service.registerUser(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(user);
	}

	@GetMapping("/list")
	public ResponseEntity<List<UserResponseDto>> listOfUsers(){
		return ResponseEntity.ok(service.listOfUsers());
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserResponseDto> findUserById(@PathVariable Integer id){
		return ResponseEntity.ok(service.getById(id));
	}

	@PutMapping("/{id}")
	public ResponseEntity<UserResponseDto> updateUser(@RequestBody UserRequestDto dto, @PathVariable Integer id){
		return ResponseEntity.ok(service.updateUser(dto, id));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
		service.deleteUser(id);
		return ResponseEntity.noContent().build();
	}
}
