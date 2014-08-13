package com.opijudge.controller.auth;

import java.util.Date;

public class AuthToken {

	private final String token;
	private final String username;
	private final Date firstTokenDate;
	private Date lastDate;
	
	public AuthToken(String token, Date firstTokenDate, String username) {
		this.token = token;
		this.firstTokenDate = firstTokenDate;
		this.username = username;
		this.setLastDate(firstTokenDate);
	}

	public String getToken() {
		return token;
	}

	public String getUsername() {
		return username;
	}

	public Date getFirstTokenDate() {
		return firstTokenDate;
	}

	public Date getLastDate() {
		return lastDate;
	}

	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}
	
}
