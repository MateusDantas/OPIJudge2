package com.opijudge.server.response;

import java.util.HashMap;

public class RankingResponse {

	private int userId;
	private String userName;
	private int totalPoints;
	private HashMap<String, Integer> hashProblems = new HashMap<String, Integer>();

	public RankingResponse() {

	}

	public RankingResponse(int userId, String userName, int totalPoints,
			HashMap<String, Integer> hashProblems) {

		this.setUserId(userId);
		this.setUserName(userName);
		this.setTotalPoints(totalPoints);
		this.setHashProblems(hashProblems);
	}
	
	public RankingResponse(int userId, String userName, HashMap<String, Integer> hashProblems) {
		
		this.setUserId(userId);
		this.setUserName(userName);
		this.setHashProblems(hashProblems);
		this.setTotalPoints(calculateTotalPoints());
	}
	
	public int calculateTotalPoints() {
		
		if (this.hashProblems == null)
			return 0;
		
		int totalPoints = 0;
		for (String key : hashProblems.keySet()) {
			totalPoints += hashProblems.get(key);
		}
		
		return totalPoints;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getTotalPoints() {
		return totalPoints;
	}

	public void setTotalPoints(int totalPoints) {
		this.totalPoints = totalPoints;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public HashMap<String, Integer> getHashProblems() {
		return hashProblems;
	}

	public void setHashProblems(HashMap<String, Integer> hashProblems) {
		this.hashProblems = hashProblems;
	}
}
