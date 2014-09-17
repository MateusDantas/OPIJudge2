package com.opijudge.server.consume;

public class ClarificationConsumes {

	private int userid;
	private int problemid;
	private int type;
	private String questionmessage;
	private String answermessage;
	private String username;
	private String token;
	
	public ClarificationConsumes() {
		
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public int getProblemid() {
		return problemid;
	}

	public void setProblemid(int problemid) {
		this.problemid = problemid;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getQuestionmessage() {
		return questionmessage;
	}

	public void setQuestionmessage(String questionmessage) {
		this.questionmessage = questionmessage;
	}

	public String getAnswermessage() {
		return answermessage;
	}

	public void setAnswermessage(String answermessage) {
		this.answermessage = answermessage;
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
	
	
}
