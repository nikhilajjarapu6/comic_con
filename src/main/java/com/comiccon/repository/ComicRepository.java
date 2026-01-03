package com.comiccon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.comiccon.entity.Comic;

public interface ComicRepository extends JpaRepository<Comic,Integer> {

}
