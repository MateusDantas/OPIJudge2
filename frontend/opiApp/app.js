(function() {

    var opiApp = angular.module('opiApp', ['ngRoute']);
    
    opiApp.config(function($routeProvider) {

	$routeProvider
	    .when('/', {
		templateUrl : 'opiApp/views/home.html',
		controller : 'mainController'
	    })
	
	    .when('/login', {
		templateUrl : 'opiApp/views/login.html',
		controller : 'loginController'
	    })
	
	    .when('/register', {
		templateUrl : 'opiApp/views/register.html',
		controller : 'registerController'
	    })
	
	    .when('submissions', {
		templateUrl : 'opiApp/views/submissions.html',
		controller : 'submissionsController'
	    })
	
	    .when('ranking', {
		templateUrl : 'opiApp/views/ranking.html',
		controller : 'rankingController'
	    })
	
	    .when('profile', {
		templateUrl : 'opiApp/views/profile.html',
		controller : 'profileController'
	    })
	
	    .when('server', {
		templateUrl : 'opiApp/views/server.html',
		controller : 'serverController'
	    })

	    .otherwise({ redirectTo: '/' });
	
	
    });

    opiApp.constant('CONSTANTS', {
	/*
	 * PATHS
	 */
	BASE_PATH = 'http://localhost:8888/',
	/*
	 * USER PATHS
	 */ 
	LOGIN_PATH = 'users/loginuser',
	LOGOUT_PATH = 'users/logoutuser',
	REGISTER_PATH = 'users/createuser',
	CHANGE_PASSWORD_PATH = 'users/changepassword',
	GET_USER_ACCESS_LEVEL_PATH = 'users/getuseraccesslevel',
	GET_USER_BY_ID_PATH = 'users/getuserbyid',
	GET_USER_BY_USERNAME_PATH = 'users/getuserbyusername',
	GET_USER_BY_EMAIL_PATH = 'users/getuserbyemail',
	/*
	 * 1. General Constants
	 */
	INTERNAL_ERROR: 10,
	OK: 11,
	UNAUTHORIZED: 12,
	INVALID_FILE: 13,
	/*
	 * 2. User Constants
	 */
	USER_ACCESS_LEVEL: 20,
	ADMIN_ACCESS_LEVEL: 21,
	INVALID_NAME: 22,
	INVALID_USERNAME: 23,
	INVALID_PASSWORD: 24,
	INVALID_EMAIL: 25,
	USERNAME_EXISTS: 26,
	EMAIL_EXISTS: 27,
	USER_ALREADY_IN: 28,
	INVALID_LOGIN: 29,
	/*
	 * 3. Submission Constants
	 */
	INVALID_USER: 31,
	INVALID_PROBLEM: 32,
	INVALID_SUBMISSION: 33,
	/*
	 * 4. Problem Constants
	 */
	INVALID_PROBLEM_NAME: 41
    });


}());

