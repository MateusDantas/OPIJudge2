(function() {

	var obiApp = angular.module('obiApp', []);

	var userFactory = function($http) {

		var basePath = opiApp.CONSTANTS.BASE_PATH;
		var factory = {};

		factory.getUserAccessLevel = function(username_, token_) {

			return $http.post(
					basePath + obiApp.CONSTANTS.GET_USER_ACCESS_LEVE_PATH, {
						username : username_,
						token : token_
					}).then(function(results) {
				return results.data;
			});

		};

		factory.getUserById = function(username_, token_, userId_) {

			return $http.post(basePath + obiApp.CONSTANTS.GET_USER_BY_ID_PATH,
					{
						username : username_,
						token : token_,
						user_id : userId_
					}).then(function(results) {
				return results.data;
			});
		};

		factory.getUserByUsername = function(username_, token_, user_username_) {

			return $http.post(
					basePath + obiApp.CONSTANTS.GET_USER_BY_USERNAME_PATH, {
						username : username_,
						token : token_,
						user_username : user_username_
					}).then(function(results) {
				return results.data;
			});
		};

		factory.getUserByEmail = function(username_, token_, email_) {

			return $http.post(
					basePath + obiApp.CONSTANTS.GET_USER_BY_EMAIL_PATH, {
						username : username_,
						token : token_,
						user_email : email_
					}).then(

			function(results) {
				return results.data;
			});

		};

		return factory;
	};

	userFactory.$inject = [ '$http' ];

	angular.module('obiApp').factory('userService', userFactory);

}());
