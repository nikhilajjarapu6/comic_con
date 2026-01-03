package com.comiccon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.comiccon.entity.Genre;

public interface GenreRepository extends JpaRepository<Genre	, Integer> {

}
