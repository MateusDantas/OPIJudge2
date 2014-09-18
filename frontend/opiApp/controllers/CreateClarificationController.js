/**
 * 
 */
(function(){
	
	var CreateClarificationController = function($scope, ngDialog, authService, clarificationService) {
		
		$scope.questionMessage = "";
		$scope.questionType = 0;
		
		$scope.saveQuestion = function() {
			
			var saveQuestionData = {
				
				token : authService.getUserToken(),
				username : authService.getUsername(),
				problemid : ngDialog.data.problemId,
				questionmessage : $scope.questionMessage,
				type : $scope.questionType
			};
			
			clarificationService.createClarification(saveQuestionData).then(function(data) {
				
				console.log(data);
				ngDialog.close();
				
			});
			
		};
		
		$scope.isUserAdmin = function() {
			
			return authService.isUserAdmin();
		};
		
	};
	
	CreateClarificationController.$inject = ['$scope', 'ngDialog', 'authService', 'clarificationService'];
	
	angular.module('opiApp').controller('CreateClarificationController', CreateClarificationController);
	
}());