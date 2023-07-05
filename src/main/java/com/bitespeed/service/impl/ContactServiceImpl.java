package com.bitespeed.service.impl;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitespeed.entity.Contact;
import com.bitespeed.repository.ContactRepository;
import com.bitespeed.service.ContactService;

@Service
public class ContactServiceImpl implements ContactService {

	@Autowired
	private ContactRepository contactRepository;
	
	@Override
	public Map<String,Object> addOrUpdateContact(HttpServletRequest request,Contact object){
		Map<String,Object>response = new HashMap<String,Object>();
		Map<String,Object>objects = new LinkedHashMap<>();
		Set<Object> phoneNumbers = new LinkedHashSet<>();
		Set<Object> emails = new LinkedHashSet<>();
		Set<Object> secondaryContactIds = new LinkedHashSet<>();
		
		List<Contact> contacts = contactRepository.findAllByEmailOrPhoneNumber(object.getEmail(),object.getPhoneNumber());

		if(contacts.size() > 0) {
			
			if(contacts.size() == 1) {
				object.setLinkedId(contacts.get(0).getId());
				object.setLinkPrecedence("secondary");
				object.setCreatedAt(new Date());
				object.setUpdatedAt(new Date());
				contactRepository.save(object);
				
			} else if (contacts.size() > 1) {
				Contact updateCon = contacts.get(1);
				updateCon.setLinkedId(contacts.get(0).getId());
				updateCon.setLinkPrecedence("secondary");
				updateCon.setUpdatedAt(new Date());
				contactRepository.save(updateCon);
				
			}
			contacts = contactRepository.getByEmailOrPhoneNumber(object.getEmail(),object.getPhoneNumber());
			for(Contact con : contacts) {
				phoneNumbers.add(con.getPhoneNumber());
				emails.add(con.getEmail());
				if(!con.getLinkPrecedence().equalsIgnoreCase("primary")) {
					secondaryContactIds.add(con.getId());
				}
			}
			objects.put("primaryContatctId", contacts.get(0).getId());
			objects.put("phoneNumbers",phoneNumbers);
			objects.put("emails",emails);
			objects.put("secondaryContactIds", secondaryContactIds);
			
		}else {
			object.setLinkedId(null);
			object.setLinkPrecedence("primary");
			object.setCreatedAt(new Date());
			object.setUpdatedAt(new Date());
			contactRepository.save(object);
			
			phoneNumbers.add(object.getPhoneNumber());
			emails.add(object.getEmail());
			
			objects.put("primaryContatctId", object.getId());
			objects.put("phoneNumbers",phoneNumbers);
			objects.put("emails",emails);
			objects.put("secondaryContactIds", secondaryContactIds);
		}
		
		
		response.put("contact", objects);
		return response;
	}
}