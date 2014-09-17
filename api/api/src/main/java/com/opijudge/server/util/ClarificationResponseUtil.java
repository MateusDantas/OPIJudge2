package com.opijudge.server.util;

import java.util.ArrayList;
import java.util.List;

import com.opijudge.controller.ProblemController;
import com.opijudge.controller.UserController;
import com.opijudge.models.Clarification;
import com.opijudge.models.Problem;
import com.opijudge.models.User;
import com.opijudge.server.response.ClarificationResponse;

public class ClarificationResponseUtil {

	public static List<ClarificationResponse> convertList(
			List<Clarification> list) {

		if (list == null)
			return null;

		List<ClarificationResponse> newList = new ArrayList<ClarificationResponse>();

		for (Clarification clarification : list) {

			int clarificationId = clarification.getId();
			int userId = clarification.getUserId();
			int problemId = clarification.getProblemId();
			int type = clarification.getType();
			String answerMessage = clarification.getAnswerMessage();
			String questionMessage = clarification.getQuestionMessage();
			String userName = "";
			String problemName = "";

			User user = UserController.getUserById(userId);
			Problem problem = ProblemController.getProblemById(problemId);

			if (user != null)
				userName = user.getName();

			if (problem != null)
				problemName = problem.getProblemName();

			newList.add(new ClarificationResponse(clarificationId, userId,
					problemId, type, questionMessage, answerMessage, userName,
					problemName));
		}
		
		return newList;
	}

}
