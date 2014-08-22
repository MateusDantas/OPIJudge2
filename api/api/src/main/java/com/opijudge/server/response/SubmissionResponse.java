package com.opijudge.server.response;

import java.util.Date;

import com.opijudge.models.Submission;

public class SubmissionResponse {

	private int id;
	private int problemId;
	private int userId;
	private int status;
	private int points;
	private long time;
	private long memory;
	private String language;
	private Date date;

	private int responseStatus;

	public SubmissionResponse() {

	}

	public SubmissionResponse(int id, int problemId, int userId, int status,
			int points, long time, long memory, String language, Date date) {

		this.setId(id);
		this.setProblemId(problemId);
		this.setUserId(userId);
		this.setStatus(status);
		this.setPoints(points);
		this.setTime(time);
		this.setMemory(memory);
		this.setLanguage(language);
		this.setDate(date);
	}
	
	public SubmissionResponse(Submission submission, int responseStatus) {
		
		this.setId(submission.getId());
		this.setProblemId(submission.getProblemId());
		this.setUserId(submission.getUserId());
		this.setStatus(submission.getStatus());
		this.setPoints(submission.getPoints());
		this.setTime(submission.getTime());
		this.setMemory(submission.getMemory());
		this.setLanguage(submission.getLanguage());
		this.setDate(submission.getDate());
		this.setResponseStatus(responseStatus);
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
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

}
