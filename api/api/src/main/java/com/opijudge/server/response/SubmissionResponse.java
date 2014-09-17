package com.opijudge.server.response;

import java.util.Date;

import com.opijudge.models.Submission;

public class SubmissionResponse {

	private int id;
	private int problemId;
	private String problemName;
	private int userId;
	private String userName;
	private String status;
	private String details;
	private int points;
	private long time;
	private long memory;
	private String language;
	private Date date;

	private int responseStatus;

	public SubmissionResponse() {

	}

	public SubmissionResponse(int id, int problemId, int userId, String status,
			int points, long time, long memory, String language, Date date,
			String details) {

		this.setId(id);
		this.setProblemId(problemId);
		this.setUserId(userId);
		this.setStatus(status);
		this.setPoints(points);
		this.setTime(time);
		this.setMemory(memory);
		this.setLanguage(language);
		this.setDate(date);
		this.setDetails(details);
	}

	public SubmissionResponse(Submission submission, int responseStatus) {

		if (submission != null) {
			this.setId(submission.getId());
			this.setProblemId(submission.getProblemId());
			this.setUserId(submission.getUserId());
			this.setStatus(submission.getStatus());
			this.setPoints(submission.getPoints());
			this.setTime(submission.getTime());
			this.setMemory(submission.getMemory());
			this.setLanguage(submission.getLanguage());
			this.setDate(submission.getDate());
			this.setDetails(submission.getDetails());
		}
		this.setResponseStatus(responseStatus);

	}

	public SubmissionResponse(Submission submission, String problemName,
			String userName, int responseStatus) {

		this(submission, responseStatus);
		this.setProblemName(problemName);
		this.setUserName(userName);

	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getProblemId() {
		return problemId;
	}

	public void setProblemId(int problemId) {
		this.problemId = problemId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public long getMemory() {
		return memory;
	}

	public void setMemory(long memory) {
		this.memory = memory;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(int responseStatus) {
		this.responseStatus = responseStatus;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getProblemName() {
		return problemName;
	}

	public void setProblemName(String problemName) {
		this.problemName = problemName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
