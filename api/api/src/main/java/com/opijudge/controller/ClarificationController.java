package com.opijudge.controller;

import java.util.List;

import com.opijudge.controller.validate.ClarificationValidate;
import com.opijudge.models.Clarification;

import static com.opijudge.controller.util.Constants.*;

public class ClarificationController {

	/**
	 * This method is responsible for creating and saving to the database
	 * a new clarification created by some user.
	 * @param problemId Id of the problem in which this clarification came from
	 * @param userId Id of the user who created this clarification
	 * @param questionMessage Question asked by the user
	 * @param type Type of clarification (0 - Private, 1 - Global)
	 * @return Returns a constant from com.opijudge.controller.util.Constants indicating what happened when
	 * the clarification was created.
	 * 
	 */
	public int createClarification(int problemId, int userId, String questionMessage, int type) {
		try {
			//TODO still needs to check the problemId and userId - Gustavo
			if (!ClarificationValidate.isClarificationValid(questionMessage, type)) {
				return INVALID_MESSAGE;
			}
			
			Clarification clarification = new Clarification(problemId, userId, type, questionMessage);
			
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
	 * This method is responsible for answering a clarification
	 * @param clarificationId
	 * @param answerMessage
	 * @return Returns a constant from com.opijudge.controller.util.Constants indicating what happened when
	 * the clarification was created.
	 */
	public int answerClarificaton(int clarificationId, String answerMessage) {
		
		return 0;
	}
	
	/**
	 * 
	 * @param clarificationId
	 * @return
	 */
	public Clarification getClarification(int clarificationId) {
		
		return null;
	}
	
	/**
	 * 
	 * @param type
	 * @return
	 */
	public List<Clarification> getAllClarificationsByType(int type) {
		
		return null;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Clarification> getAllClarifications() {
		
		return null;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public List<Clarification> getUnansweredClarifications() {
		
		return null;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public List<Clarification> getClarificationsByUser() {
		
		return null;
	}

}
