package com.opijudge.models;

import java.io.File;

public class ProblemDAOImpl implements ProblemDAO {

	private Problem problem = null;
	private File testPlan = null, testCases = null, statement = null;
	private String basePath;

	public ProblemDAOImpl(Problem problem, File testPlan, File testCases,
			File statement, String basePath) {

		this.setProblem(problem);
		this.setTestPlan(testPlan);
		this.setTestCases(testCases);
		this.setStatement(statement);
		this.setBasePath(basePath);
	}

	public boolean saveTestPlan() {

		return saveFile(testPlan, "testplan.txt");
	}

	public boolean saveTestCases() {

		return saveFile(testCases, "testcases.zip");
	}

	public boolean saveStatement() {

		return saveFile(statement, "statement.pdf");
	}

	public boolean saveAll() {

		return saveTestPlan() && saveTestCases() && saveStatement();
	}

	public boolean saveFile(File file, String endPath) {

		try {
			if (file == null)
				return false;

			if (!file.canWrite())
				return false;

			String totalPath = getTotalPath();

			if (totalPath == null)
				return false;

			totalPath += endPath;

			if (file.renameTo(new File(totalPath)))
				return true;
			else
				return false;
		} catch (Exception e) {

			return false;
		}
	}

	public boolean loadTestPlan() {
		
		File file = loadFile(getTotalPath() + "testplan.txt");
		if (file == null)
			return false;
		this.setTestPlan(file);
		return true;
	}

	public boolean loadTestCases() {
		
		File file = loadFile(getTotalPath() + "testcases.zip");
		if (file == null)
			return false;
		this.setTestCases(file);
		return true;
	}

	public boolean loadStatement() {
		
		File file = loadFile(getTotalPath() + "statement.pdf");
		if (file == null)
			return false;
		this.setStatement(file);
		return true;
	}

	public boolean loadAll() {
		
		return loadTestPlan() && loadTestCases() && loadStatement();
	}

	public File loadFile(String totalPath) {
		
		File file = new File(totalPath);
		if (!file.isFile())
			return null;
		if (!file.canRead())
			return null;
		return file;
	}
	
	public String getTotalPath() {

		int curId = problem.getId();
		if (curId < 1)
			return null;

		String path = basePath;
		if (path.endsWith("/"))
			path += String.valueOf(curId) + "/";
		else
			path += "/" + String.valueOf(curId) + "/";

		return path;
	}

	public Problem getProblem() {
		return problem;
	}

	public void setProblem(Problem problem) {
		this.problem = problem;
	}

	public File getTestPlan() {
		return testPlan;
	}

	public void setTestPlan(File testPlan) {
		this.testPlan = testPlan;
	}

	public File getTestCases() {
		return testCases;
	}

	public void setTestCases(File testCases) {
		this.testCases = testCases;
	}

	public File getStatement() {
		return statement;
	}

	public void setStatement(File statement) {
		this.statement = statement;
	}

	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

}
