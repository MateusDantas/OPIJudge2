package com.opijudge.controller;

import static com.opijudge.controller.util.Constants.*;

import java.util.HashMap;
import java.util.List;

import com.opijudge.controller.validate.UserValidate;
import com.opijudge.models.User;

public class UserController {

	public UserController() {

	}

	public int registerUser(String name, String username, String password,
			String email, int accessLevel, int isBlocked) {

		if (!UserValidate.isNameValid(name))
			return INVALID_NAME;

		if (!UserValidate.isUsernameValid(username))
			return INVALID_USERNAME;

		if (!UserValidate.isPasswordValid(password))
			return INVALID_PASSWORD;

		if (!UserValidate.isEmailValid(email))
			return INVALID_EMAIL;
		
		if (getUserByUsername(username) != null)
			return USERNAME_EXISTS;
		
		if (getUserByEmail(email) != null)
			return EMAIL_EXISTS;

		User user = new User(name, username, password, email, accessLevel,
				isBlocked);
		if (!user.saveToDatabase())
			return INTERNAL_ERROR;

		return OK;
	}

	public int registerAdmin(String name, String username, String password,
			String email) {

		return registerUser(name, username, password, email,
				ADMIN_ACCESS_LEVEL, 0);
	}

	public int registerRegularUser(String name, String username,
			String password, String email) {

		return registerUser(name, username, password, email, USER_ACCESS_LEVEL,
				1);
	}

	public User getUserById(int id) {

		return getUserByArgument("ID", id);
	}

	public User getUserByUsername(String username) {

		return getUserByArgument("USERNAME", username);
	}

	public User getUserByEmail(String email) {

		return getUserByArgument("EMAIL", email);
	}
	
	public <T> User getUserByArgument(String argument, T value) {
		
		try {
			User user = new User();
			HashMap<String, T> mapKeys = new HashMap<String, T>();
			mapKeys.put(argument, value);
			
			@SuppressWarnings("unchecked")
			List<User> list = (List<User>) user.getAllByProperty(mapKeys);

			if (list == null)
				return null;

			if (list.size() == 0)
				return null;

			return list.get(0);
			
		} catch (Exception e) {

			return null;
		}
	}
}
