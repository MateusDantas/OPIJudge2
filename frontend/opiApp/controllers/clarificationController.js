/**
 * 
 */
(function() {

	var opiApp = angular.module('opiApp');

	var ClarificationController = function($scope, $location, authService,
			clarificationService, CONSTANTS) {

		$scope.clarifications = null;
		
		$scope.username = authService.getUsername();
		$scope.token = authService.getUserToken();
		
		
		$scope.getClarifications = function() {
			
			if (authService.isUserAdmin()) {
				clarificationService.getUnansweredClarifications($scope.username, $scope.token).then(function(data) {
					$scope.clarifications = data;
				});
			} else {
				clarificationService.getClarificationsByUser($scope.username, $scope.token).then(function(data) {
					$scope.clarifications = data;
				});
			}
			
		};
		
		$scope.isUserAdmin = function() {
			
			return authService.isUserAdmin();
		};
	};

	ClarificationController.$inject = [ '$scope', '$location', 'authService',
			'clarificationService', 'CONSTANTS' ];

	angular.module('opiApp').controller('ClarificationController',
			ClarificationController);
})();