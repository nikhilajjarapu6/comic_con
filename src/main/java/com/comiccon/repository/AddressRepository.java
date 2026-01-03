package com.comiccon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.comiccon.entity.Address;

public interface AddressRepository extends JpaRepository<Address,Integer> {

}
