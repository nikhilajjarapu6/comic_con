package com.comiccon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.comiccon.entity.Payment;
import com.comiccon.entity.Rating;

public interface RatingRepository extends JpaRepository<Rating,Integer> {

	

}
