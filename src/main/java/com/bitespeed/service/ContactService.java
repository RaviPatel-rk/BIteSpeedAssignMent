package com.bitespeed.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.bitespeed.entity.Contact;

public interface ContactService {

	public Map<String,Object> addOrUpdateContact(HttpServletRequest request,Contact object);
		
}