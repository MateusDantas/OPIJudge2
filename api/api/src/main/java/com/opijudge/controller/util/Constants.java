package com.opijudge.controller.util;

public class Constants {
	
	/*
	 * 1. General
	 */
	public static final int INTERNAL_ERROR = 10;
	public static final int OK = 11;
	public static final int UNAUTHORIZED = 12;
	public static final int INVALID_FILE = 13;
	public static final String SERVER_SECRET_KEY = "309E127J-D021K7S21D7JE9-D#@873214#%DS-odulas029318-*-*";
	/*
	 * 2. User Constants
	 */
	public static final int USER_ACCESS_LEVEL = 20;
	public static final int ADMIN_ACCESS_LEVEL = 21;
	public static final int INVALID_NAME = 22;
	public static final int INVALID_USERNAME = 23;
	public static final int INVALID_PASSWORD = 24;
	public static final int INVALID_EMAIL = 25;
	public static final int USERNAME_EXISTS = 26;
	public static final int EMAIL_EXISTS = 27;
	public static final int USER_ALREADY_IN = 28;
	public static final int INVALID_LOGIN = 29;
	public static final int USER_IS_BLOCKED = 200;
	/*
	 * 3. Submission Constants
	 */
	public static final int INVALID_USER = 31;
	public static final int INVALID_PROBLEM = 32;
	public static final int INVALID_SUBMISSION = 33;
	public static final String SUBMISSION_PATH = "/home/mdantas/codes-opijudge/";
	public static final long LIMIT_SUBMISSION_SIZE = 10485760L;
	/*
	 * 4. Problems Constants
	 */
	public static final int INVALID_PROBLEM_NAME = 41;
	public static final String PROBLEM_PATH = "/home/mdantas/problems-opijudge/";
	public static final long LIMIT_PROBLEM_SIZE = 10L * 10485760L;
	/*
	 * 5. Server Variables 
	 */
	public static final String JUDGE_HOSTNAME = "localhost";
	public static final int JUDGE_PORT = 8722;
	/*
	 *6. Clarification Variables
	 */
	public static final int INVALID_MESSAGE = 60;
	public static final int INVALID_TYPE = 61;
	public static final int INVALID_PROBLEM_ID = 62;
	public static final int INVALID_USER_ID = 63;
	public static final int INVALID_CLARIFICATION = 64;
	public static final int CLARIFICATION_ALREADY_ANSWERED  = 65;
	public static final int PRIVATE_TYPE = 0;
	public static final int GLOBAL_TYPE = 1;
	
	
}
