package com.opijudge.controller.validate;

import com.opijudge.controller.ProblemController;

public class ProblemValidate {

	public static boolean isProblemNameValid(String problemName) {
		
		return problemName.length() > 1 && problemName.length() < 25;
	}
	
	public static boolean isProblemIdValid(int problemId) {
		return ProblemController.getProblemById(problemId) != null;
	}
}
