(function() {

	var opiApp = angular.module('opiApp');

	var clarificationFactory = function($http, CONSTANTS) {

		var basePath = CONSTANTS.BASE_PATH;

		var factory = {};

		factory.createClarification = function(data) {

			$http.post(
					CONSTANTS.BASE_PATH + CONSTANTS.CREATE_CLARIFICATION_PATH,
					data).then(function(result) {
				return result.data;
			});
		};

		factory.changeClarification = function(data) {

			$http.put(
					CONSTANTS.BASE_PATH + CONSTANTS.CHANGE_CLARIFICATION_PATH,
					data).then(function(result) {
				return result.data;
			});
		};

		factory.getClarificationsByUser = function(username, token) {

			$http.get(
					CONSTANTS.BASE_PATH
							+ CONSTANTS.GET_CLARIFICATIONS_BY_USER_PATH
							+ "?username=" + username + "&token=" + token)
					.then(function(result) {
						return result.data;
					});
		};

		factory.getUnansweredClarifications = function(username, token) {

			$http.get(
					CONSTANTS.BASE_PATH
							+ CONSTANTS.GET_UNANSWERED_CLARIFICATIONS_PATH
							+ "?username=" + username + "&token=" + token)
					.then(function(result) {
						return result.data;
					});
		};
		
		factory.getAllClarifications = function(username, token) {

			$http.get(
					CONSTANTS.BASE_PATH
							+ CONSTANTS.GET_ALL_CLARIFICATIONS_PATH
							+ "?username=" + username + "&token=" + token)
					.then(function(result) {
						return result.data;
					});
		};

		return factory;
	};

	clarificationFactory.$inject = [ '$http', 'CONSTANTS' ];

	angular.module('opiApp').factory('clarificationService',
			clarificationFactory);

}());
