package com.opijudge.models;

import com.opijudge.models.util.BCrypt;

public class User extends Entity {

	private int id;
	private String email, password, username, hashedPassword;
	private int accessLevel, isBlocked;

	public User() {

	}

	public User(int id) {

		this.setId(id);
	}

	public User(String email, String password, String username,
			int accessLevel, int isBlocked) {

		this.setEmail(email);
		this.setPassword(password);
		this.setHashedPassword(hashPassword(password));
		this.setAccessLevel(accessLevel);
		this.setIsBlocked(isBlocked);
	}

	public String hashPassword(String curPassword) {

		return BCrypt.hashpw(curPassword, BCrypt.gensalt(4));
	}

	public String hashPassword(String curPassword, int salt) throws Exception {

		return BCrypt.hashpw(curPassword, BCrypt.gensalt(salt));
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getIsBlocked() {
		return isBlocked;
	}

	public void setIsBlocked(int isBlocked) {
		this.isBlocked = isBlocked;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAccessLevel() {
		return accessLevel;
	}

	public void setAccessLevel(int accessLevel) {
		this.accessLevel = accessLevel;
	}

	public String getHashedPassword() {
		return hashedPassword;
	}

	public void setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}
}
