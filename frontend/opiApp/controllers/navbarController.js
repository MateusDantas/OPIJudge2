(function() {

	var NavbarController = function($scope, authService) {

		$scope.userData = {
			username : authService.getUsername(),
			token : authService.getUserToken()
		};

		$scope.isUserLoggedIn = function() {

			return authService.isAuthenticated();
		};

		$scope.isUserAdmin = function() {

			return authService.isUserAdmin();
		};

		$scope.logoutUser = function() {

			authService.logout($scope.userData).then(function(status) {

				console.log('hey');
				authService.deleteUserData();
				authService.redirectToLogin();

			});
		};

	};

	NavbarController.$inject = [ '$scope', 'authService' ];

	angular.module('opiApp').controller('NavbarController', NavbarController);
}());