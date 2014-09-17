package com.opijudge.models;

public class Clarification extends Entity {

	private int id;
	private int problemId;
	private int userId;
	private int type;
	private String questionMessage;
	private String answerMessage;
	
	public Clarification() {
		
	}
	
	public Clarification(int problemId, int userId, int type, String questionMessage) {
		
		this.setProblemId(problemId);
		this.setUserId(userId);
		this.setType(type);
		this.setQuestionMessage(questionMessage);
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

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getQuestionMessage() {
		return questionMessage;
	}

	public void setQuestionMessage(String message) {
		this.questionMessage = message;
	}

	public String getAnswerMessage() {
		return answerMessage;
	}

	public void setAnswerMessage(String answerMessage) {
		this.answerMessage = answerMessage;
	}
	
	
}
