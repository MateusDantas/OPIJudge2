package com.opijudge.controller.validate;

public class ProblemValidate {

	public static boolean isProblemNameValid(String problemName) {
		
		return problemName.length() > 1 && problemName.length() < 25;
	}
}
