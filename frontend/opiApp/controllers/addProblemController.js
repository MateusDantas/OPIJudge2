/**
 * 
 */
(function() {
	
	var AddProblemController = function($scope, problemService, authService, CONSTANTS) {
		
		$scope.problemData = {
			username : authService.getUsername(),
			token : authService.getUserToken(),
			problemName : null,
			problemType : 1,
			testPlan : null,
			testCases : null,
			statement : null
		};
		
		$scope.returnMessage = '';
		
		$scope.addProblem = function(problemData) {
			
			console.log(problemData);
			
			problemService.createProblem(problemData).then(function(status) {

				if (parseInt(status) !== CONSTANTS.OK) {
					$scope.returnMessage = 'Unable to create problem!';
				} else {
					$scope.returnMessage = 'Problem added successfuly!';
				}
			});
		};
	};
	
	AddProblemController.$inject = ['$scope', 'problemService', 'authService', 'CONSTANTS'];
	angular.module('opiApp').controller('AddProblemController', AddProblemController);
}());