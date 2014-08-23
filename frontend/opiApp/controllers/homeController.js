/**
 * 
 */
(function() {

	var HomeController = function($scope, authService, problemService,
			submissionService, CONSTANTS) {

		$scope.userData = {
			username : authService.getUsername(),
			token : authService.getUserToken()
		};

		$scope.problems = [];

		$scope.selectedProblem = null;

		$scope.submissions = [];

		$scope.submissionData = {

			username : authService.getUsername(),
			token : authService.getUserToken(),
			problemid : null,
			file : null
		};

		$scope.problemData = {

			username : authService.getUsername(),
			token : authService.getUserToken(),
			problemid : null
		};

		$scope.getProblems = function() {

			problemService
					.getAllProblems($scope.userData)
					.then(
							function(data) {

								if (data.responseStatus === CONSTANTS.OK) {
									$scope.problems = data.list;
									if ($scope.problems.length != 0) {
										$scope.selectedProblem = $scope.problems[0];
										$scope.submissionData.problemid = $scope.problems[0].id;
										$scope.problemData.problemid = $scope.problems[0].id;
									}
								} else {
									$scope.problems = [];
								}

							});
		};

		$scope.$watch('selectedProblem', function() {

			if ($scope.selectedProblem != null) {
				console.log('mudou');
				$scope.getSubmissions();
			}

		});

		$scope.getSubmissions = function() {

			var getSubmissionData = {

				username : authService.getUsername(),
				token : authService.getUserToken(),
				problemid : $scope.selectedProblem.id,
				userid : authService.getUserId()

			};

			submissionService.getSubmissionByUserInProblem(getSubmissionData)
					.then(function(data) {

						console.log(data);

						if (data.responseStatus === CONSTANTS.OK) {
							$scope.submissions = data.list;
							console.log($scope.submissions);
						} else {
							$scope.submissions = [];
						}
					});

		};

		$scope.submitSolution = function() {

			$scope.submissionData.problemid = $scope.selectedProblem.id;
			console.log(JSON.stringify($scope.submissionData.file));

			submissionService.makeSubmission($scope.submissionData).then(
					function(status) {

						console.log(status);
					});
		};

		$scope.getStatementLink = function() {
			
			var PATH = CONSTANTS.BASE_PATH + CONSTANTS.GET_PROBLEM_STATEMENT_PATH + '?';
			PATH += "username=" + $scope.problemData.username;
			PATH += "&token=" + $scope.problemData.token;
			PATH += "&problemid=" + $scope.problemData.problemid;
			
			return PATH;
		};

		$scope.changeProblemView = function(problem) {

			$scope.selectedProblem = problem;
			$scope.submissionData.problemid = problem.id;
			$scope.problemData.problemid = problem.id;
		};

		$scope.getRealDate = function(curDate) {

			console.log(curDate);
			var objDate = new Date(parseInt(curDate));

			return objDate.toString();
		};

		$scope.getClassName = function(index) {

			if (parseInt(index) % 2 == 0)
				return "btn btn-danger";
			return "btn btn-custom";
		};

	};

	HomeController.$inject = [ '$scope', 'authService', 'problemService',
			'submissionService', 'CONSTANTS' ];

	angular.module('opiApp').controller('HomeController', HomeController);
}());