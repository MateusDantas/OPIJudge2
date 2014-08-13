package com.opijudge.models;

public class Problem extends Entity {

	private int id, problemType;
	private String problemName;

	public Problem(int id) {
		this.setId(id);
	}

	public Problem(int problemType, String problemName) {
		this.setProblemType(problemType);
		this.setProblemName(problemName);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProblemName() {
		return problemName;
	}

	public void setProblemName(String problemName) {
		this.problemName = problemName;
	}

	public int getProblemType() {
		return problemType;
	}

	public void setProblemType(int problemType) {
		this.problemType = problemType;
	}
}
