/**
 * 
 */
(function() {
	
	var RegisterController = function($scope, $location, $routeParams, authService, CONSTANTS) {
		
		var path = '/';
		
		$scope.data = {
			name : '',
			username : '',
			email : '',
			password : ''
		};
		
		$scope.returnMessage = '';

		$scope.register = function(data) {
			
			$scope.returnMessage = "Creating your user... Please, wait!";
			authService.register(data).then(function(status){
				
				console.log('REGISTER COMPLETED, STATUS: ' + status);

				if (parseInt(status) !== CONSTANTS.OK) {
					
					$scope.returnMessage = getErrorMessage(status);
					return;
				}

				authService.redirectToLogin();
			});
		};
		
		function getErrorMessage(status) {
			
			switch (status) {
			case '10':
				return "Internal error, contact admin";
				break;
			case '11':
				return "OK";
				break;
			case '12':
				return "Forbidden request, contact admin";
				break;
			case '13':
				return "Invalid file";
				break;
			case '22':
				return "Invalid name, must be between 4 and 20 characters";
				break;
			case '23':
				return "Invalid username, must be between 4 and 20 characters";
				break;
			case '24':
				return "Invalid password";
				break;
			case '25':
				return "Invalid email";
				break;
			case '26':
				return "This username is already in use";
				break;
			case '27':
				return "This email is already in use";
				break;
			case '28':
				return "User already logged in";
				break;
			default:
				return "Internal error, contact admin";
				break;
			}
		}
	};
	
	RegisterController.$inject = ['$scope', '$location', '$routeParams', 'authService', 'CONSTANTS'];
	
	angular.module('opiApp').controller('RegisterController', RegisterController);

})();