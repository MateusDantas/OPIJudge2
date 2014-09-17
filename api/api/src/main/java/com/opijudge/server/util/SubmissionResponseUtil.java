package com.opijudge.server.util;

import java.util.ArrayList;
import java.util.List;

import com.opijudge.server.response.SubmissionResponse;
import com.opijudge.controller.ProblemController;
import com.opijudge.controller.UserController;
import com.opijudge.models.Problem;
import com.opijudge.models.Submission;
import com.opijudge.models.User;

public class SubmissionResponseUtil {

	public static List<SubmissionResponse> convertList(List<Submission> list,
			boolean setProblemName, boolean setUserName) {

		List<SubmissionResponse> listResponse = new ArrayList<SubmissionResponse>();

		if (list == null)
			listResponse = null;
		else {
			for (int i = 0; i < list.size(); i++) {
				
				SubmissionResponse submissionResponse = new SubmissionResponse(list.get(i), 0);
				
				if (setProblemName) {
					
					Problem problem = ProblemController.getProblemById(submissionResponse.getProblemId());
					if (problem != null)
						submissionResponse.setProblemName(problem.getProblemName());
				}
				
				if (setUserName) {
					
					User user = UserController.getUserById(submissionResponse.getUserId());
					if (user != null)
						submissionResponse.setUserName(user.getUsername());
				}
				
				listResponse.add(submissionResponse);
			}
		}

		return listResponse;
	}
	
	public static List<SubmissionResponse> convertList(List<Submission> list) {
		
		return convertList(list, false, false);
	}
}
