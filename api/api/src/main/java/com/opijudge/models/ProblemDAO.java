package com.opijudge.models;

public interface ProblemDAO {

	public boolean saveTestPlan();
	public boolean saveTestCases();
	public boolean saveStatement();
	public boolean saveAll();
	public boolean loadTestPlan();
	public boolean loadTestCases();
	public boolean loadStatement();
	public boolean loadAll();
}
