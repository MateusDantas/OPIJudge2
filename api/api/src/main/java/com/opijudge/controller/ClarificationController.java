package com.opijudge.controller;

import java.util.HashMap;
import java.util.List;

import com.opijudge.controller.validate.ClarificationValidate;
import com.opijudge.controller.validate.ProblemValidate;
import com.opijudge.controller.validate.UserValidate;
import com.opijudge.models.Clarification;

import static com.opijudge.controller.util.Constants.*;

public class ClarificationController {

	/**
	 * This method is responsible for creating and saving to the database a new
	 * clarification created by some user.
	 * 
	 * @param problemId
	 *            Id of the problem in which this clarification came from
	 * @param userId
	 *            Id of the user who created this clarification
	 * @param questionMessage
	 *            Question asked by the user
	 * @param type
	 *            Type of clarification (0 - Private, 1 - Global)
	 * @return Returns a constant from com.opijudge.controller.util.Constants
	 *         indicating what happened when the clarification was created.
	 * 
	 */
	public static int createClarification(int problemId, int userId,
			String questionMessage, int type) {
		try {
			if (!ClarificationValidate.isClarificationMessageValid(questionMessage)) {
				return INVALID_MESSAGE;
			}
			if (!ClarificationValidate.isClarificationTypeValid(type)) {
				return INVALID_TYPE;
			}
			if (!ProblemValidate.isProblemIdValid(problemId)) {
				return INVALID_PROBLEM_ID;
			}
			if (!UserValidate.isUserIdValid(userId)) {
				return INVALID_USER_ID;
			}
			
			Clarification clarification = new Clarification(problemId, userId,
					type, questionMessage);

			if (!clarification.saveToDatabase()) {
				return INTERNAL_ERROR;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return INTERNAL_ERROR;
		}
		return OK;
	}

	/**
	 * This method is responsible for answering a clarification.
	 * 
	 * @param clarificationId
	 * @param answerMessage
	 * @param newType
	 * @return Returns a constant from com.opijudge.controller.util.Constants
	 *         indicating what happened when the clarification was created.
	 */
	public static int answerClarification(int clarificationId,
			String answerMessage, int newType) {
		try {
			if (!ClarificationValidate.isClarificationIdValid(clarificationId)) {
				return INVALID_CLARIFICATION;
			}			
			if (!ClarificationValidate.isClarificationMessageValid(answerMessage)) {
				return INVALID_MESSAGE;
			}
			if (ClarificationValidate.isClarificationAnswered(clarificationId)) {
				return CLARIFICATION_ALREADY_ANSWERED;
			}
			Clarification clarification = ClarificationController.getClarification(clarificationId);
			clarification.setAnswerMessage(answerMessage);
			clarification.setType(newType);
			// Mateus to update the Database we use this method?
			if (!clarification.saveToDatabase()) {
				return INTERNAL_ERROR;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return INTERNAL_ERROR;
		}
		return OK;
	}
	
	/**
	 * 
	 * @param clarificationId
	 * @param answerMessage
	 * @return
	 */
	public static int answerClarification(int clarificationId, String answerMessage) {
		if (!ClarificationValidate.isClarificationIdValid(clarificationId)) {
			return INVALID_CLARIFICATION;
		}		
		Clarification clarification = ClarificationController.getClarification(clarificationId);
		return answerClarification(clarificationId, answerMessage, clarification.getId());
	}

	/**
	 * 
	 * @param clarificationId
	 * @return
	 */
	public static Clarification getClarification(int clarificationId) {

		HashMap<String, Integer> mapKeys = new HashMap<String, Integer>();
		mapKeys.put("id", clarificationId);

		List<Clarification> list = getClarificationsByProperty(mapKeys);

		if (list == null || list.size() == 0)
			return null;

		return list.get(0);
	}

	/**
	 * Return a list of all clarifications of some type
	 * @param type
	 * @return
	 */
	public static List<Clarification> getAllClarificationsByType(int type) {

		HashMap<String, Integer> mapKeys = new HashMap<String, Integer>();
		mapKeys.put("type", type);

		List<Clarification> list = getClarificationsByProperty(mapKeys);

		if (list == null || list.size() == 0)
			return null;

		return list;
	}

	/**
	 * 
	 * @return
	 */
	public static List<Clarification> getAllClarifications() {
		HashMap<String, String> mapKeys = new HashMap<String, String>();

		List<Clarification> list = getClarificationsByProperty(mapKeys);

		return list;
	}

	/**
	 * Return a list of all unanswered clarifications.
	 * @return
	 */
	public static List<Clarification> getUnansweredClarifications() {

		HashMap<String, String> mapKeys = new HashMap<String, String>();
		mapKeys.put("answerMessage", null);
		mapKeys.put("answerMessage", "");
		
		List<Clarification> list = getClarificationsByProperty(mapKeys);

		if (list == null || list.size() == 0)
			return null;

		return list;
	}

	/**
	 * Return a list of all clarifications created by user
	 * @return
	 */
	public static List<Clarification> getClarificationsByUser(int userId) {

		HashMap<String, Integer> mapKeys = new HashMap<String, Integer>();
		mapKeys.put("userId", userId);

		List<Clarification> list = getClarificationsByProperty(mapKeys);

		if (list == null || list.size() == 0)
			return null;

		return list;
	}
	
	public static <T> List<Clarification> getClarificationsByProperty(
			HashMap<String, T> mapKeys) {
		try {

			if (mapKeys == null)
				return null;

			Clarification clarification = new Clarification();

			@SuppressWarnings("unchecked")
			List<Clarification> list = (List<Clarification>) clarification
					.getAllByProperty(mapKeys);

			return list;

		} catch (Exception e) {

			return null;
		}
		
	}


}
