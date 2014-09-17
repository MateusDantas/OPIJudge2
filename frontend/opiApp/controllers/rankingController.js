(function() {

	var RankingController = function($scope, authService, problemService,
			userService, CONSTANTS) {

		$scope.problems = null;

		$scope.rankingData = {

			username : authService.getUsername(),
			token : authService.getUserToken()
		};

		$scope.users = null;

		$scope.getProblems = function() {

			problemService.getAllProblems($scope.rankingData).then(function(data) {

				console.log(data);
				if (data.responseStatus === CONSTANTS.OK) {
					$scope.problems = data.list;
				} else {
					$scope.problems = [];
				}

			});
		};
	

		$scope.getRanking = function() {
			
			userService.getRanking($scope.rankingData).then(function(data) {
				
				$scope.users = data;
			});
		};
		
		
		$scope.initAll = function() {
			
			$scope.getProblems();
			$scope.getRanking();
		};
	
	};

	RankingController.$inject = [ '$scope', 'authService', 'problemService',
			'userService', 'CONSTANTS' ];

	angular.module('opiApp').controller('RankingController', RankingController);
}());