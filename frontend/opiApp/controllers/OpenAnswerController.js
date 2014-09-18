/**
 * 
 */
(function(){
	
	var OpenAnswerController = function($scope, ngDialog, authService, clarificationService) {
		
		$scope.answerMessage = ngDialog.data.answerMessage;
		$scope.questionType = ngDialog.data.type;
		
		$scope.saveAnswer = function() {
			
			var saveAnswerData = {
				
				token : authService.getUserToken(),
				username : authService.getUsername(),
				problemid : ngDialog.data.problemId,
				answermessage : $scope.answerMessage,
				type : $scope.questionType
			};
			
			clarificationService.changeClarification(saveQuestionData).then(function(data) {
				
				console.log(data);
				ngDialog.close();
				
			});
			
		};
		
		$scope.isUserAdmin = function() {
			
			return authService.isUserAdmin();
		};
		
	};
	
	OpenAnswerController.$inject = ['$scope', 'ngDialog', 'authService', 'clarificationService'];
	
	angular.module('opiApp').controller('OpenAnswerController', OpenAnswerController);
	
}());