package com.bitespeed.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bitespeed.entity.Contact;
import com.bitespeed.service.ContactService;

@RestController
public class ContactController {

	@Autowired
	private ContactService contactService;
	
	@RequestMapping(value="/identify", method = RequestMethod.POST)
	public ResponseEntity<?> identify(HttpServletRequest request, @RequestBody Contact contact) {
		return ResponseEntity.ok(this.contactService.addOrUpdateContact(request, contact));
	}
}