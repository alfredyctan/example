package org.afc.petstore.api.delegate;

import java.util.List;

import org.afc.petstore.api.UserApiDelegate;
import org.afc.petstore.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class UserApiDelegateImpl implements UserApiDelegate {

	private static final Logger logger = LoggerFactory.getLogger(UserApiDelegateImpl.class);
	
	@Override
	public ResponseEntity<Void> createUser(User body) {
		logger.info("createUser : {}", body);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@Override
	public ResponseEntity<Void> deleteUser(String username) {
		logger.info("deleteUser : {}", username);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@Override
	public ResponseEntity<User> getUserByName(String username) {
		logger.info("getUserByName : {}", username);
		User user = new User();
		user.setEmail("hola@afc.org");
		user.setFirstName("Ho");
		user.setId(1L);
		user.setLastName("Lo");
		user.setPhone("999");
		user.setUsername("hola");
		user.userStatus(1);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> loginUser(String username, String password) {
		logger.info("loginUser : {}, {}", username, password);
		if ("pass".equals(password)) {
			return new ResponseEntity<String>("Success", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Fail", HttpStatus.OK);
		}
	}

	@Override
	public ResponseEntity<Void> logoutUser() {
		logger.info("logoutUser");
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@Override
	public ResponseEntity<Void> updateUser(String username, User body) {
		logger.info("updateUser : {}, {}", username, body);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@Override
	public ResponseEntity<Void> createUsersWithArrayInput(List<User> body) {
		logger.info("createUsersWithArrayInput : {}", body);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@Override
	public ResponseEntity<Void> createUsersWithListInput(List<User> body) {
		logger.info("createUsersWithListInput : {}", body);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
