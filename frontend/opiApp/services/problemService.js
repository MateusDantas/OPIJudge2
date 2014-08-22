/**
 * 
 */
(function() {

	var opiApp = angular.module('opiApp');

	var problemFactory = function($http, CONSTANTS) {

		var basePath = CONSTANTS.BASE_PATH;
		var factory = {};

		factory.createProblem = function(username_, token_, problemName_,
				problemType_, testPlan, testCases, statement) {

			var formData = new FormData();
			formData.append('testplan', testPlan);
			formData.append('testcases', testCases);
			formData.append('statement', statement);
			formData.append('username', username_);
			formData.append('token', token_);
			formData.append('problemname', problemName_);
			formData.append('problemtype', problemType_);

			return $http.post(basePath + CONSTANTS.CREATE_PROBLEM_PATH,
					formData, {
						transformRequest : angular.identity,
						headers : {
							'Content-Type' : undefined
						}
					}).then(function(results) {
				return results.data;
			});

		};

		factory.getProblemStatement = function(data) {

			return $http.post(
					basePath + CONSTANTS.GET_PROBLEM_STATEMENT_PATH,
					data).then(function(results) {
				return results.data;
			});
		};

		factory.getAllProblems = function(data) {

			console.log(data);
			return $http.post(
					basePath + CONSTANTS.GET_ALL_PROBLEMS_PATH, data)
					.then(function(results) {
						return results.data;
					});
		};
		
		return factory;
	};

	problemFactory.$inject = [ '$http', 'CONSTANTS' ];
	angular.module('opiApp').factory('problemService', problemFactory);

})();