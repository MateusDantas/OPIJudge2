package com.opijudge.controller.validate;

import static com.opijudge.controller.util.Constants.*;

public class ClarificationValidate {
	public static boolean isClarificationValid(String questionMessage, int type) {
		boolean isValid = true;
		if (type != PRIVATE_TYPE || 
		    type != GLOBAL_TYPE) {
			isValid = false;
		}
		if (questionMessage == null ||
			questionMessage.equals("")) {
			isValid = false;
		}
		return isValid;
	}
}
