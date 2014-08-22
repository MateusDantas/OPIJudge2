package com.opijudge.server.response;

import com.opijudge.models.Problem;

public class ProblemResponse {

	private int id, problemType;
	private String problemName;
	
	public ProblemResponse() {
		
	}
	
	public ProblemResponse(Problem problem) {
		if (problem != null) {
			this.setId(problem.getId());
			this.setProblemType(problem.getProblemType());
			this.setProblemName(problem.getProblemName());
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getProblemType() {
		return problemType;
	}

	public void setProblemType(int problemType) {
		this.problemType = problemType;
	}

	public String getProblemName() {
		return problemName;
	}

	public void setProblemName(String problemName) {
		this.problemName = problemName;
	}
}
