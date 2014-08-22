package com.opijudge.controller.validate;

import java.util.regex.*;

import com.opijudge.models.util.BCrypt;

public class UserValidate {

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	public static boolean isEmailValid(String email) {
		
		return true;
		//return Pattern.compile(EMAIL_PATTERN).matcher(email).matches();
	}
	
	public static boolean isUsernameValid(String username) {
		
		if (username == null)
			return false;
		
		return username.length() > 2 && username.length() < 15;
	}
	
	public static boolean isNameValid(String name) {
		
		if (name == null)
			return false;
		
		return name.length() > 3 && name.length() < 50;
	}
	
	public static boolean isPasswordValid(String password) {
		
		if (password == null)
			return false;
		
		return password.length() > 5 && password.length() <= 20;
	}

	public static boolean checkPassword(String textPassword, String hashedPassword) {
		
		boolean result;
		try {
			result = BCrypt.checkpw(textPassword, hashedPassword);
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		
		return result;
	}
}
