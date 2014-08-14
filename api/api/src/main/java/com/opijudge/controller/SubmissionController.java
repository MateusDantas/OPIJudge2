package com.opijudge.controller;

import java.io.File;
import java.io.InputStream;
import java.util.Date;

import com.opijudge.controller.auth.AuthTokenManager;
import com.opijudge.models.Problem;
import com.opijudge.models.Submission;
import com.opijudge.models.User;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.opijudge.controller.util.FileUtil;

import static com.opijudge.controller.util.Constants.*;

public class SubmissionController {

	public SubmissionController() {

	}

	public int makeSubmission(int problemId, String username, String token,
			InputStream inputStream, FormDataContentDisposition fileDetail) {

		try {
			if (!AuthTokenManager.isUserAuthentic(token, username))
				return INVALID_USER;

			User user = UserController.getUserByUsername(username);

			if (user == null)
				return INVALID_USER;

			if (user.getId() < 1)
				return INVALID_USER;

			File file = FileUtil.convertToFile(inputStream, fileDetail,
					SUBMISSION_PATH + String.valueOf(user.getId()));

			if (!file.isFile() || !file.canRead())
				return INVALID_FILE;

			if (file.length() > LIMIT_SUBMISSION_SIZE) {
				file.delete();
				return INVALID_FILE;
			}

			Problem problem = ProblemController.getProblemById(problemId);
			if (problem == null)
				return INVALID_PROBLEM;

			Submission submission = new Submission(problemId, user.getId(),
					FileUtil.getFileExtension(file), new Date());
			if (!submission.saveToDatabase())
				return INTERNAL_ERROR;

			if (!ServerController.singleSubmission(submission.getId()))
				return INTERNAL_ERROR;

		} catch (Exception e) {

			e.printStackTrace();
			return INTERNAL_ERROR;
		}

		return OK;
	}

	public static int rejudgeSubmission(String username, String token,
			int submissionId) {

		try {
			if (!AuthTokenManager.isUserAuthentic(token, username))
				return INVALID_USER;

			User user = UserController.getUserByUsername(username);
			if (user == null || user.getId() < 1)
				return INVALID_USER;

			if (user.getAccessLevel() != ADMIN_ACCESS_LEVEL)
				return UNAUTHORIZED;

			if (!ServerController.rejudgeSubmission(submissionId))
				return INTERNAL_ERROR;

		} catch (Exception e) {

			e.printStackTrace();
			return INTERNAL_ERROR;
		}

		return OK;
	}
}
