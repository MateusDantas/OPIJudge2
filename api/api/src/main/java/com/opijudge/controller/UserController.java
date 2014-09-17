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

	public static int registerUser(String name, String username,
			String password, String email, int accessLevel, int isBlocked) {

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
		System.out.println("User " + username);
		System.out.println("User " + user.getUsername());
		if (!user.saveToDatabase())
			return INTERNAL_ERROR;

		return OK;
	}

	public static int registerAdmin(String name, String username,
			String password, String email) {

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

		if (Integer.valueOf(id) == null)
			return null;

		List<User> list = getUserByArgument("id", id);
		if (list == null || list.size() == 0)
			return null;

		return list.get(0);
	}

	public static User getUserByUsername(String username) {

		if (username == null)
			return null;

		List<User> list = getUserByArgument("username", username);
		if (list == null || list.size() == 0)
			return null;

		return list.get(0);
	}

	public static User getUserByEmail(String email) {

		if (email == null)
			return null;

		List<User> list = getUserByArgument("email", email);
		if (list == null || list.size() == 0)
			return null;

		return list.get(0);
	}

	public static List<User> getAllUsers() {

		HashMap<String, String> mapKeys = new HashMap<String, String>();

		List<User> list = getUserByProperty(mapKeys);

		return list;
	}

	public static <T> List<User> getUserByArgument(String argument, T value) {

		HashMap<String, T> mapKeys = new HashMap<String, T>();
		mapKeys.put(argument, value);

		return getUserByProperty(mapKeys);
	}

	public static <T> List<User> getUserByProperty(HashMap<String, T> mapKeys) {

		try {
			User user = new User();

			@SuppressWarnings("unchecked")
			List<User> list = (List<User>) user.getAllByProperty(mapKeys);

			return list;

		} catch (Exception e) {

			return null;
		}
	}

	public static int loginUser(String username, String password) {

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

		AuthTokenManager.removeAuthToken(token);
		AuthTokenManager.removeUserToken(username);

		return OK;
	}

	public static int changePassword(String username, String newPassword,
			String token) {

		User user = getUserByUsername(username);
		user.setPassword(newPassword);
		user.setHashedPassword(user.hashPassword(newPassword));

		if (!user.saveToDatabase())
			return INTERNAL_ERROR;

		return OK;
	}

}
