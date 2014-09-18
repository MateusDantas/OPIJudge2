package com.opijudge.controller.validate;

import static com.opijudge.controller.util.Constants.*;

import com.opijudge.controller.ClarificationController;
import com.opijudge.models.Clarification;

public class ClarificationValidate {
	public static boolean isClarificationMessageValid(String questionMessage) {
		boolean isValid = true;
		if (questionMessage == null ||
			questionMessage.equals("")) {
			isValid = false;
		}
		return isValid;
	}
	
	public static boolean isClarificationTypeValid(int type) {
		boolean isValid = true;
		if (type != PRIVATE_TYPE || 
		    type != GLOBAL_TYPE) {
			isValid = false;
		}
		return isValid;
	}
	
	public static boolean isClarificationAnswered(int clarificationId) {
		Clarification clarification = ClarificationController.getClarification(clarificationId);
		String answerMessage = clarification.getAnswerMessage();
		
		return !(answerMessage == null || answerMessage.equals(""));
	}
	
	public static boolean isClarificationIdValid(int clarificationId) {
		Clarification clarification = ClarificationController.getClarification(clarificationId);
				
		return clarification != null;
	}
}
