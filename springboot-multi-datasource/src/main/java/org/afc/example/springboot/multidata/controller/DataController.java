package org.afc.example.springboot.multidata.controller;

import java.util.List;

import org.afc.example.springboot.multidata.model.UserGroup;
import org.afc.example.springboot.multidata.model.Users;
import org.afc.example.springboot.multidata.repository.crud.UserGroupCrudRepository;
import org.afc.example.springboot.multidata.repository.crud.UsersCrudRepository;
import org.afc.example.springboot.multidata.repository.jpa.UserGroupJpaRepository;
import org.afc.example.springboot.multidata.repository.jpa.UsersJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/data")
public class DataController {

	private static final Logger logger = LoggerFactory.getLogger(DataController.class);
	
//	@Autowired
//	private UsersCrudRepository usersCrudRepository;
//	
	@Autowired
	private UsersJpaRepository usersJpaRepository;
	
//	@Autowired
//	private UserGroupCrudRepository userGroupCrudRepository;

	@Autowired
	private UserGroupJpaRepository userGroupJpaRepository;
	
	
	@RequestMapping(value = "/test", produces = { "text/plain" }, method = RequestMethod.GET)
	public ResponseEntity<String> getDRusergroupData() {
		
//		List<Users> users = usersCrudRepository.findById(1);
//		logger.info("users : {}", users);
//
//		users = usersCrudRepository.findAll();
//		logger.info("users : {}", users);

		Users user = usersJpaRepository.findById(2);
		logger.info("user : {}", user);

//		List<UserGroup> usergroups = userGroupCrudRepository.findById(1);
//		logger.info("usergroups : {}", usergroups);
//
//		usergroups = userGroupCrudRepository.findAll();
//		logger.info("usergroups : {}", usergroups);

		UserGroup usergroup = userGroupJpaRepository.findById(2);
		logger.info("usergroup : {}", usergroup);

		return new ResponseEntity<String>("test", HttpStatus.OK);
	}
}
