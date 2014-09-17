(function() {

	var opiApp = angular.module('opiApp');

	var authFactory = function($http, $rootScope, $location, $window, CONSTANTS) {

		var basePath = CONSTANTS.BASE_PATH;

		var factory = {
			loginPath : CONSTANTS.LOGIN_PATH,
			logoutPath : CONSTANTS.LOGOUT_PATH,
			registerPath : CONSTANTS.REGISTER_PATH,
		};

		factory.login = function(credentials) {

			return $http
					.post(basePath + factory.loginPath, credentials)
					.then(
							function(results) {

								var username = results.data.username;
								var token = results.data.token;
								var userRole = results.data.accessLevel;
								var userId = results.data.id;

								if (parseInt(results.data.responseStatus) === CONSTANTS.OK)
									factory.insertUserData(username, token,
											userRole, userId);
								else
									factory.deleteUserData();

								return results.data.responseStatus;
							});
		};

		factory.logout = function(data) {

			console.log(data);
			return $http
					.post(basePath + factory.logoutPath, data)
					.then(
							function(results) {

								console.log(results);
								var isLoggedIn = parseInt(results.data) !== CONSTANTS.OK;

								if (!isLoggedIn)
									factory.deleteUserData();

								return isLoggedIn;
							});
		};

		factory.register = function(data) {

			console.log(data);

			return $http.post(basePath + factory.registerPath, data).then(
					function(results) {
						return results.data;
					});
		};

		factory.redirectToLogin = function() {

			console.log('Broadcast redirect');
			$location.path('/login');
		};

		factory.isAuthenticated = function() {

			if (!$window.localStorage.token)
				return false;

			return true;
		};

		factory.isUserAdmin = function() {

			if (parseInt(getUserRole()) === parseInt(CONSTANTS.ADMIN_ACCESS_LEVEL))
				return true;

			return false;
		};

		factory.getUsername = function() {

			return getUsername();
		}

		factory.getUserToken = function() {

			return getUserToken();
		}

		factory.getUserRole = function() {

			return getUserRole();
		}

		factory.getUserId = function() {

			return getUserId();
		}

		factory.insertUserData = function(username, token, userRole, userId) {

			setUsername(username);
			setUserToken(token);
			setUserRole(userRole);
			setUserId(userId);
		}

		factory.deleteUserData = function() {

			deleteUsername();
			deleteUserToken();
			deleteUserRole();
			deleteUserId();
		}

		$rootScope.$on('deleteUserData', function() {
			
			factory.deleteUserData();
			factory.redirectToLogin();
		});
		
		function setUsername(username) {
			$window.localStorage.username = username;
		}

		function getUsername() {
			return $window.localStorage.username;
		}

		function deleteUsername() {
			delete $window.localStorage.username;
		}

		function setUserRole(userRole) {
			$window.localStorage.userRole = userRole;
		}

		function getUserRole() {
			return $window.localStorage.userRole;
		}

		function deleteUserRole() {
			delete $window.localStorage.userRole;
		}

		function setUserToken(userToken) {
			$window.localStorage.token = userToken;
		}

		function getUserToken() {
			return $window.localStorage.token;
		}

		function deleteUserToken() {
			delete $window.localStorage.token;
		}

		function setUserId(userId) {
			$window.localStorage.userId = parseInt(userId);
		}

		function getUserId() {
			return parseInt($window.localStorage.userId);
		}

		function deleteUserId() {
			delete $window.localStorage.userId;
		}

		return factory;
	};

	authFactory.$inject = [ '$http', '$rootScope', '$location', '$window',
			'CONSTANTS' ];

	angular.module('opiApp').factory('authService', authFactory);

}());
