package com.opijudge.controller.util;

public class Constants {
	
	/*
	 * 1. General Errors
	 */
	public static final int INTERNAL_ERROR = 10;
	public static final int OK = 11;
	public static final int UNAUTHORIZED = 12;
	public static final int INVALID_FILE = 13;
	/*
	 * 2. User Constants
	 */
	public static final int USER_ACCESS_LEVEL = 0;
	public static final int ADMIN_ACCESS_LEVEL = 1;
	public static final int INVALID_NAME = 20;
	public static final int INVALID_USERNAME = 21;
	public static final int INVALID_PASSWORD = 22;
	public static final int INVALID_EMAIL = 23;
	public static final int USERNAME_EXISTS = 24;
	public static final int EMAIL_EXISTS = 25;
	public static final int USER_ALREADY_IN = 26;
	public static final int INVALID_LOGIN = 27;
	/*
	 * 3. Submission Constants
	 */
	public static final int INVALID_USER = 31;
	public static final int INVALID_PROBLEM = 32;
	public static final String SUBMISSION_PATH = "/var/codes-opijudge/";
	public static final long LIMIT_SUBMISSION_SIZE = 10485760L;
	/*
	 * 4. Problems Constants
	 */
	public static final int INVALID_PROBLEM_NAME = 41;
	public static final String PROBLEM_PATH = "/var/problems-opijudge/";
	public static final long LIMIT_PROBLEM_SIZE = 10L * 10485760L;
	
}