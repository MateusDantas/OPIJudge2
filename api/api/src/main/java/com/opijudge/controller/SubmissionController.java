package com.opijudge.controller;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.criterion.Order;

import com.opijudge.controller.auth.AuthTokenManager;
import com.opijudge.models.Problem;
import com.opijudge.models.Submission;
import com.opijudge.models.SubmissionDAOImpl;
import com.opijudge.models.User;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.opijudge.controller.util.FileUtil;

import static com.opijudge.controller.util.Constants.*;

public class SubmissionController {

	public SubmissionController() {

	}

	public static int makeSubmission(int problemId, String username,
			String token, InputStream inputStream,
			FormDataContentDisposition fileDetail) {

		try {
			if (!AuthTokenManager.isUserAuthentic(token, username))
				return INVALID_USER;

			User user = UserController.getUserByUsername(username);

			if (user == null)
				return INVALID_USER;

			if (user.getId() < 1)
				return INVALID_USER;

			Problem problem = ProblemController.getProblemById(problemId);
			if (problem == null)
				return INVALID_PROBLEM;

			Submission submission = new Submission(problemId, user.getId(),
					FileUtil.getFileExtension(fileDetail.getFileName()),
					new Date());

			if (!submission.saveToDatabase())
				return INTERNAL_ERROR;

			File file = FileUtil.convertToFile(
					inputStream,
					fileDetail,
					SUBMISSION_PATH
							+ String.valueOf(submission.getId())
							+ "."
							+ FileUtil.getFileExtension(fileDetail
									.getFileName()));

			if (!file.isFile() || !file.canRead())
				return INVALID_FILE;

			if (file.length() > LIMIT_SUBMISSION_SIZE) {
				file.delete();
				return INVALID_FILE;
			}

			if (!ServerController.singleSubmission(submission.getId()))
				return INTERNAL_ERROR;

		} catch (Exception e) {

			e.printStackTrace();
			return INTERNAL_ERROR;
		}

		return OK;
	}

	
	public static Submission getMaxSubmissionInProblem(int userId, int problemId) {
		
		HashMap<String, Integer> mapKeys = new HashMap<String, Integer>();
		mapKeys.put("problemId", problemId);
		mapKeys.put("userId", userId);
		
		List<Order> orders = new ArrayList<Order>();
		
		orders.add(Order.desc("points"));
		
		List<Submission> list = getSubmissionsByProperty(mapKeys, orders, 1);
		
		if (list == null || list.size() == 0)
			return null;
		
		return list.get(0);
	}

	public static List<Submission> getSubmissionsByUserInProblem(int userId,
			int problemId) {

		HashMap<String, Integer> mapKeys = new HashMap<String, Integer>();
		mapKeys.put("problemId", problemId);
		mapKeys.put("userId", userId);

		return getSubmissionsByProperty(mapKeys, null, -1);
	}

	public static List<Submission> getSubmissionsByProblem(int problemId) {

		HashMap<String, Integer> mapKeys = new HashMap<String, Integer>();
		mapKeys.put("problemId", problemId);

		return getSubmissionsByProperty(mapKeys, null, -1);
	}

	public static List<Submission> getSubmissionsByUser(int userId) {

		HashMap<String, Integer> mapKeys = new HashMap<String, Integer>();
		mapKeys.put("userId", userId);

		return getSubmissionsByProperty(mapKeys, null, -1);
	}

	public static Submission getSubmissionById(int submissionId) {

		HashMap<String, Integer> mapKeys = new HashMap<String, Integer>();
		mapKeys.put("id", submissionId);

		List<Submission> list = getSubmissionsByProperty(mapKeys, null, -1);

		if (list == null || list.size() == 0)
			return null;

		return list.get(0);
	}
	
	public static <T> List<Submission> getLastSubmissions(int limitSize, int indexPage) {
		
		HashMap<String, String> mapKeys = new HashMap<String, String>();
		
		List<Order> orders = new ArrayList<Order>();
		
		orders.add(Order.desc("id"));
		
		List<Submission> list = getSubmissionsByProperty(mapKeys, orders, limitSize * indexPage);
		
		if (list == null || list.size() == 0)
			return null;
		
		List<Submission> listResponse = new ArrayList<Submission>();
		
		for (int i = list.size() - 1; i >= Math.max(0, list.size() - limitSize); i--) {
			listResponse.add(list.get(i));
		}
		
		return listResponse;
	}

	public static <T> List<Submission> getSubmissionsByProperty(
			HashMap<String, T> mapKeys, List<Order> orders, int limitSize) {

		try {

			if (mapKeys == null)
				return null;

			Submission submission = new Submission();

			@SuppressWarnings("unchecked")
			List<Submission> list = (List<Submission>) submission
					.getAllByProperty(mapKeys, orders, limitSize);

			return list;

		} catch (Exception e) {

			return null;
		}
	}

	public static File getSubmissionCode(int submissionId) {

		Submission submission = getSubmissionById(submissionId);

		if (submission == null)
			return null;

		String submissionPath = SUBMISSION_PATH;

		SubmissionDAOImpl submissionDAO = new SubmissionDAOImpl(submission,
				submissionPath);

		if (!submissionDAO.loadCode())
			return null;

		System.out.println("codeLoaded");
		return submissionDAO.getCode();
	}
}
