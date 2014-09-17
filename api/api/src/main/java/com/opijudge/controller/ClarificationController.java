package com.opijudge.controller;

import com.opijudge.models.Clarification;

import static com.opijudge.controller.util.Constants.*;

public class ClarificationController {

	/**
	 * This method is responsible for creating and saving to the database
	 * a new clarification created by some user.
	 * @param userId Id of the user who created this clarification
	 * @param problemId Id of the problem in which this clarification came from
	 * @param questionMessage Question asked by the user
	 * @param type Type of clarification (0 - Private, 1 - Global)
	 * @return Returns a constant from com.opijudge.controller.util.Constants indicating what happened when
	 * the clarification was created.
	 * 
	 */
	public int createClarification(int userId, int problemId, String questionMessage, int type) {
		
		return 0;
	}
	
	/**
	 * This method is responsible for answering a clarification
	 * @param clarificationId
	 * @param answerMessage
	 * @return
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
	public Clarification getAllClarificationsByType(int type) {
		
		return null;
	}
	
	/**
	 * 
	 * @return
	 */
	public Clarification getAllClarifications() {
		
		return null;
	}
	
}
