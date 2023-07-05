package com.bitespeed.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bitespeed.entity.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer>{

	@Query(value="Select c from com.bitespeed.entity.Contact c where c.email=:email or c.phoneNumber=:phoneNumber and c.linkPrecedence = 'primary'")
	List<Contact> findAllByEmailOrPhoneNumber(String email, String phoneNumber);

	@Query(value="Select c from com.bitespeed.entity.Contact c where c.email=:email or c.phoneNumber=:phoneNumber")
	List<Contact> getByEmailOrPhoneNumber(String email, String phoneNumber);
}
