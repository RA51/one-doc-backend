package com.doc.central.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doc.central.app.domain.Users;
import com.doc.central.app.repository.UsersRepository;
import com.doc.central.app.service.UsersService;
@Service
public class UsersServiceImpl implements UsersService {
    
	@Autowired
	private UsersRepository repo;
	@Override
	public Users saveProfiles(Users users) {
		return repo.save(users);
	}

}
