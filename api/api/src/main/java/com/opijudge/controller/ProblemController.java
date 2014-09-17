package com.opijudge.controller;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.opijudge.controller.util.FileUtil;
import com.opijudge.controller.util.ZipUtil;
import com.opijudge.controller.validate.ProblemValidate;
import com.opijudge.models.Problem;
import com.opijudge.models.ProblemDAOImpl;
import com.opijudge.models.Submission;
import com.sun.jersey.core.header.FormDataContentDisposition;

import static com.opijudge.controller.util.Constants.*;

public class ProblemController {

	public static int saveProblem(String problemName, int problemType,
			String username, String token, InputStream testPlanStream,
			FormDataContentDisposition testPlanDetail,
			InputStream testCasesStream,
			FormDataContentDisposition testCasesDetail,
			InputStream statementStream,
			FormDataContentDisposition statementDetail) {

		try {
			if (!ProblemValidate.isProblemNameValid(problemName))
				return INVALID_PROBLEM_NAME;

			Problem problem = new Problem(problemType, problemName);

			if (!problem.saveToDatabase())
				return INTERNAL_ERROR;

			String problemPath = PROBLEM_PATH + "/"
					+ String.valueOf(problem.getId()) + "/";

			File testPlanFile = FileUtil.convertToFile(testPlanStream,
					testPlanDetail, problemPath + "testplan.txt");

			File testCasesFile = FileUtil.convertToFile(testCasesStream,
					testCasesDetail, problemPath + "testcases.zip");

			File statementFile = FileUtil.convertToFile(statementStream,
					statementDetail, problemPath + "statement.pdf");

			if (testPlanFile == null || testCasesFile == null
					|| statementFile == null) {

				problem.deleteFromDatabase();
				return INTERNAL_ERROR;
			}

			ZipUtil.decompressFile(problemPath + "testcases.zip", problemPath);

		} catch (Exception e) {

			e.printStackTrace();
			return INTERNAL_ERROR;
		}

		return OK;
	}

	public static HashMap<String, Integer> getUserScore(int userId) {
		
		List<Problem> problems = getAllProblems();
		if (problems == null)
			return null;
		
		HashMap<String, Integer> hashProblems = new HashMap<String, Integer>();
		
		for (Problem problem : problems) {
			Submission bestSubmission = SubmissionController.getMaxSubmissionInProblem(userId, problem.getId());
			if (bestSubmission == null)
				hashProblems.put(problem.getProblemName(), 0);
			else
				hashProblems.put(problem.getProblemName(), bestSubmission.getPoints());
		}
		
		return hashProblems;
	}
	
	public static Problem getProblemById(int problemId) {

		HashMap<String, Integer> mapKeys = new HashMap<String, Integer>();
		mapKeys.put("id", problemId);

		List<Problem> list = getProblemsByProperty(mapKeys);

		if (list == null || list.size() == 0)
			return null;

		return list.get(0);
	}

	public static List<Problem> getProblemsByName(String problemName) {

		HashMap<String, String> mapKeys = new HashMap<String, String>();
		mapKeys.put("problemName", problemName);

		List<Problem> list = getProblemsByProperty(mapKeys);

		return list;
	}

	public static List<Problem> getAllProblems() {

		HashMap<String, String> mapKeys = new HashMap<String, String>();

		List<Problem> list = getProblemsByProperty(mapKeys);

		return list;
	}

	public static <T> List<Problem> getProblemsByProperty(
			HashMap<String, T> mapKeys) {

		try {

			if (mapKeys == null)
				return null;

			Problem problem = new Problem();

			@SuppressWarnings("unchecked")
			List<Problem> list = (List<Problem>) problem
					.getAllByProperty(mapKeys);

			return list;

		} catch (Exception e) {

			return null;
		}
	}

	public static File getProblemStatement(int problemId) {

		Problem problem = getProblemById(problemId);

		if (problem == null)
			return null;

		String problemPath = PROBLEM_PATH;

		System.out.println(problemPath
				+ String.valueOf(problem.getId()) + "/" + "statement.pdf");

		ProblemDAOImpl problemDAO = new ProblemDAOImpl(problem, problemPath);
		if (!problemDAO.loadStatement())
			return null;

		return problemDAO.getStatement();
	}
}
