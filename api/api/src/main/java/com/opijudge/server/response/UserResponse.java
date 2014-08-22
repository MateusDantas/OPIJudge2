package com.opijudge.server.response;

import com.opijudge.models.User;

public class UserResponse {

	private int id;
	private int responseStatus;
	private int accessLevel;
	private String name;
	private String username;
	private String token;

	public UserResponse() {

	}

	public UserResponse(User user, String token, int responseStatus) {

		if (user != null) {
			this.setAccessLevel(user.getAccessLevel());
			this.setName(user.getName());
			this.setUsername(user.getUsername());
			this.setId(user.getId());
		}

		this.setToken(token);
		this.setResponseStatus(responseStatus);
	}

	public UserResponse(String token, int responseStatus) {

		this.setToken(token);
		this.setResponseStatus(responseStatus);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(int responseStatus) {
		this.responseStatus = responseStatus;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getAccessLevel() {
		return accessLevel;
	}

	public void setAccessLevel(int accessLevel) {
		this.accessLevel = accessLevel;
	}

}
