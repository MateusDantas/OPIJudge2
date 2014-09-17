package com.opijudge.server.response;

public class ClarificationResponse {

	private int clarificationId;
	private int userId;
	private int problemId;
	private int type;
	private String questionMessage;
	private String answerMessage;
	private String userName;
	private String problemName;

	public ClarificationResponse() {

	}

	public ClarificationResponse(int clarificationId, int userId,
			int problemId, int type, String questionMessage, String answerMessage,
			String userName, String problemName) {

		this.setClarificationId(clarificationId);
		this.setUserId(userId);
		this.setProblemId(problemId);
		this.setType(type);
		this.setQuestionMessage(questionMessage);
		this.setAnswerMessage(answerMessage);
		this.setUserName(userName);
		this.setProblemName(problemName);
	}

	public int getClarificationId() {
		return clarificationId;
	}

	public void setClarificationId(int clarificationId) {
		this.clarificationId = clarificationId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getProblemId() {
		return problemId;
	}

	public void setProblemId(int problemId) {
		this.problemId = problemId;
	}

	public String getQuestionMessage() {
		return questionMessage;
	}

	public void setQuestionMessage(String questionMessage) {
		this.questionMessage = questionMessage;
	}

	public String getAnswerMessage() {
		return answerMessage;
	}

	public void setAnswerMessage(String answerMessage) {
		this.answerMessage = answerMessage;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getProblemName() {
		return problemName;
	}

	public void setProblemName(String problemName) {
		this.problemName = problemName;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
