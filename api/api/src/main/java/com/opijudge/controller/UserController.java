package com.opijudge.controller;

import static com.opijudge.controller.util.Constants.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.opijudge.controller.auth.AuthToken;
import com.opijudge.controller.auth.AuthTokenManager;
import com.opijudge.controller.util.RandomToken;
import com.opijudge.controller.validate.UserValidate;
import com.opijudge.models.User;

public class UserController {

	public UserController() {

	}

	public static int registerUser(String name, String username, String password,
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

	public static int registerAdmin(String name, String username, String password,
			String email) {

		return registerUser(name, username, password, email,
				ADMIN_ACCESS_LEVEL, 0);
	}

	public static int registerRegularUser(String name, String username,
			String password, String email) {

		return registerUser(name, username, password, email, USER_ACCESS_LEVEL,
				1);
	}

	public static int getUserAccessLevel(String username) {
		
		User user = getUserByUsername(username);
		if (user == null)
			return INVALID_USER;
		
		return user.getAccessLevel();
	}
	
	public static User getUserById(int id) {

		List <User> list = getUserByArgument("ID", id);
		if (list == null || list.size() == 0)
			return null;
		
		return list.get(0);
	}

	public static User getUserByUsername(String username) {

		List<User> list =  getUserByArgument("USERNAME", username);
		if (list == null || list.size() == 0)
			return null;
		
		return list.get(0);
	}

	public static User getUserByEmail(String email) {

		List<User> list = getUserByArgument("EMAIL", email);
		if (list == null || list.size() == 0)
			return null;
		
		return list.get(0);
	}
	
	public static <T> List<User> getUserByArgument(String argument, T value) {
		
		try {
			User user = new User();
			HashMap<String, T> mapKeys = new HashMap<String, T>();
			mapKeys.put(argument, value);
			
			@SuppressWarnings("unchecked")
			List<User> list = (List<User>) user.getAllByProperty(mapKeys);

			return list;
			
		} catch (Exception e) {

			return null;
		}
	}
	
	public static int loginUser(String username, String password) {
		
		if (AuthTokenManager.getTokenUser(username) != null)
			return USER_ALREADY_IN;
		
		User user = getUserByUsername(username);
		
		if (user == null)
			return INVALID_LOGIN;
		
		if (!UserValidate.checkPassword(password, user.getHashedPassword()))
			return INVALID_LOGIN;
		
		String token = RandomToken.generateToken();
		AuthToken userToken = new AuthToken(token, new Date(), username);
		AuthTokenManager.addToken(token, userToken);
		
		return OK;
	}
	
	public static int logoutUser(String username, String token) {
		
		if (!AuthTokenManager.isUserAuthentic(token, username))
			return UNAUTHORIZED;
		
		AuthTokenManager.removeAuthToken(token);
		AuthTokenManager.removeUserToken(username);
		
		return OK;
	}
	
	public static int changePassword(String username, String newPassword, String token) {
		
		if (!AuthTokenManager.isUserAuthentic(token, username))
			return UNAUTHORIZED;
		
		User user = getUserByUsername(username);
		user.setPassword(newPassword);
		user.setHashedPassword(user.hashPassword(newPassword));
		
		if (!user.saveToDatabase())
			return INTERNAL_ERROR;
		
		return OK;
	}

}
