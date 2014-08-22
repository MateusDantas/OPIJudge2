/**
 * 
 */
(function(){

	var NavController = function ($scope, $location, authService) {
		
		
		function redirectToLogin() {
			
			$location.path('/login');
		}
		
		$scope.on('logoutUser', function() {
			console.log('here');
			authService.deleteUserData();
			authService.redirectToLogin();
		});
		
		$scope.$on('redirectToLogin', function() {
			console.log('Redirecting to login!');
			redirectToLogin();
		});
	};
	
	NavController.$inject = ['$scope', '$location', 'authService'];
	
	angular.module('opiApp').controller('NavController', NavController);
	
}());