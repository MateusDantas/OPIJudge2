/**
 * 
 */
(function() {

	var opiApp = angular.module('opiApp');

	var problemFactory = function($http, CONSTANTS) {

		var basePath = CONSTANTS.BASE_PATH;
		var factory = {};

		factory.createProblem = function(data) {

			var formData = new FormData();
			
			formData.append('testplan', data.testPlan);
			formData.append('testcases', data.testCases);
			formData.append('statement', data.statement);
			formData.append('username', data.username);
			formData.append('token', data.token);
			formData.append('problemname', data.problemName);
			formData.append('problemtype', data.problemType);

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