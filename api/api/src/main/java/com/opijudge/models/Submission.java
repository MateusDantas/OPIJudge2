package com.opijudge.models;

import java.util.Date;

public class Submission extends Entity {

	private int id;
	private int problemId;
	private int userId;
	private int points;
	private long time, memory;
	private String language;
	private String status = "";
	private String details = "";
	private Date date;

	public Submission() {
		
	}
	
	public Submission(int id) {

		this.setId(id);
	}

	public Submission(int problemId, int userId, String status, int points,
			long time, long memory, String language, Date date, String details) {

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
	
	public Submission(int problemId, int userId, String language, Date date) {

		this.setProblemId(problemId);
		this.setUserId(userId);
		this.setLanguage(language);
		this.setDate(date);
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
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

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
}
