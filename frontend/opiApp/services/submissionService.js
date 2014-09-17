/**
 * 
 */
(function() {

	var opiApp = angular.module('opiApp');

	var submissionFactory = function($http, CONSTANTS) {

		var basePath = CONSTANTS.BASE_PATH;
		var factory = {};

		factory.makeSubmission = function(data) {

			var formData = new FormData();
			console.log(JSON.stringify(data.file));

			formData.append('file', data.file);
			console.log(JSON.stringify(formData));
			formData.append('problemid', data.problemid);
			formData.append('username', data.username);
			formData.append('token', data.token);

			/*$http({
				method: 'POST',
				data: formData,
				url: basePath + CONSTANTS.MAKE_SUBMISSION_PATH,
				headers: {'Content-Type','multipart/form-data'}
			})*/
			console.log(formData);
			return $http.post(basePath + CONSTANTS.MAKE_SUBMISSION_PATH, formData, {
				transformRequest : angular.identity,
				headers : {
					'Content-Type' : undefined
				}
			}).then(function(results) {
				return results.data;
			});

		};

		factory.getSubmissionById = function(submissionId_, username_, token_) {

			return $http.post(basePath + CONSTANTS.GET_SUBMISSION_BY_ID_PATH, {
				submissionid : submissionId_,
				username : username_,
				token : token_
			}).then(function(results) {
				return results.data;
			});
		};

		factory.getSubmissionByUser = function(userId_, username_, token_) {

			return $http.post(basePath + CONSTANTS.GET_SUBMISSION_BY_USER_PATH,
					{
						userid : userId_,
						username : username_,
						token : token_
					}).then(function(results) {
				return results.data;
			});
		};

		factory.getLastSubmissions = function(data) {
			console.log(data);
			return $http.post(basePath + CONSTANTS.GET_LAST_SUBMISSIONS, data).then(function(results) {
				return results.data;
			});
		};
		
		factory.getSubmissionByProblem = function(problemId_, username_, token_) {

			return $http.post(
					basePath + CONSTANTS.GET_SUBMISSION_BY_PROBLEM_PATH, {
						problemid : problemId_,
						username : username_,
						token : token_
					}).then(function(results) {
				return results.data;
			});
		};

		factory.rejudgeSubmission = function(data) {
			
			return $http.post(basePath + CONSTANTS.REJUDGE_SUBMISSION_PATH, data).then(function(results) {
				
				return results.data;
			});
		};
		
		factory.getSubmissionByUserInProblem = function(data) {

			return $http.post(
					basePath + CONSTANTS.GET_SUBMISSION_BY_USER_IN_PROBLEM,
					data).then(function(results) {
				return results.data;
			});
		};

		factory.getUserCode = function(submissionId_, username_, token_) {

			return $http.post(basePath + CONSTANTS.GET_USER_CODE, {
				submissionid : submissionId_,
				username : username_,
				token : token_
			}).then(function(results) {
				return results.data;
			});
		};

		return factory;

	};

	submissionFactory.$inject = [ '$http', 'CONSTANTS' ];

	angular.module('opiApp').factory('submissionService', submissionFactory);

})();