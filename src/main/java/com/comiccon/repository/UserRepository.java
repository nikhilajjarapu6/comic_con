package com.comiccon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.comiccon.entity.User;

public interface UserRepository extends JpaRepository<User,Integer> {
//	Boolean existByEmail(String email);
	User findByEmail(String email);
}
