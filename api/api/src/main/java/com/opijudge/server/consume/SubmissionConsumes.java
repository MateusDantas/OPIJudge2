package com.opijudge.server.consume;

public class SubmissionConsumes {

	private int userid;
	private int problemid;
	private int submissionid;
	private int limitsize;
	private int indexpage;
	private String username;
	private String token;
	
	public SubmissionConsumes() {
		
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

	public int getSubmissionid() {
		return submissionid;
	}

	public void setSubmissionid(int submissionid) {
		this.submissionid = submissionid;
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

	public int getLimitSize() {
		return limitsize;
	}

	public void setLimitSize(int limitSize) {
		this.limitsize = limitSize;
	}

	public int getLimitsize() {
		return limitsize;
	}

	public void setLimitsize(int limitsize) {
		this.limitsize = limitsize;
	}

	public int getIndexpage() {
		return indexpage;
	}

	public void setIndexpage(int indexpage) {
		this.indexpage = indexpage;
	}
	
}
