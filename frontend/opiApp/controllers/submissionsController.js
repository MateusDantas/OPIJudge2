/**
 * 
 */
(function() {

	var SubmissionsController = function($scope, $routeParams, $location,
			submissionService, authService, CONSTANTS) {

		$scope.lastSubmissions = null;

		$scope.submissionsData = {
			limitsize : 25,
			indexpage : 1,
			username : authService.getUsername(),
			token : authService.getUserToken()
		};

		$scope.getLastSubmissions = function() {

			submissionService.getLastSubmissions($scope.submissionsData).then(
					function(data) {

						console.log('cheguei');
						console.log(data);
						if (parseInt(data.responseStatus) == CONSTANTS.OK) {
							$scope.lastSubmissions = data.list;
						} else {
							$scope.lastSubmissions = null;
						}
					});
		};

		$scope.rejudgeSubmission = function(submissionId) {
			
			var rejudgeData = {
				username : authService.getUsername(),
				token : authService.getUserToken(),
				submissionid : submissionId
			};
			
			submissionService.rejudgeSubmission(rejudgeData).then(function(data) {
				
				if (data === "true") {;
					console.log('rejudged');
				}
				
			});
		};
		
		$scope.getSubmissions = function() {

			$scope.submissionsData.indexpage = 1;
			$scope.getLastSubmissions();
		};

		$scope.backPage = function() {

			if ($scope.submissionsData.indexpage > 1) {
				$scope.submissionsData.indexpage -= 1;
				$scope.getLastSubmissions();
			}
		};

		$scope.nextPage = function() {

			if ($scope.lastSubmissions[0].id != 1) {
				$scope.submissionsData.indexpage += 1;
				$scope.getLastSubmissions();
			} else {
				console.log('hihihih');
			}
		};

		$scope.getCodeLink = function(submissionId) {

			var PATH = CONSTANTS.BASE_PATH + CONSTANTS.GET_USER_CODE + '?';
			PATH += "username=" + $scope.submissionsData.username;
			PATH += "&token=" + $scope.submissionsData.token;
			PATH += "&submissionid=" + submissionId;

			return PATH;
		};

	};

	SubmissionsController.$inject = [ '$scope', '$routeParams', '$location',
			'submissionService', 'authService', 'CONSTANTS' ];

	angular.module('opiApp').controller('SubmissionsController',
			SubmissionsController);
}());