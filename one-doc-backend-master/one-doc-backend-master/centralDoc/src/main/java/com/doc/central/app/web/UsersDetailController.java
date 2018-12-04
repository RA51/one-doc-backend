package com.doc.central.app.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.doc.central.app.domain.Users;
import com.doc.central.app.service.UsersService;

@RestController
@RequestMapping(value="/rest")
@Api(value = "Users Detail Controller ", description = "Operations pertaining Vendor Master", basePath = "/documentation")
public class UsersDetailController {
	
	@Autowired
	private UsersService userservice;
	
	@RequestMapping(value = "/services/saveProfiles",method = RequestMethod.POST, consumes = "application/json", produces="application/json")
	@ResponseBody
	@ApiOperation(value = "User class")
	public Users saveProfile(@RequestBody Users users)
	{
		return userservice.saveProfiles(users);
		
	}

}
