/**
 * 
 */
(function() {

	var opiApp = angular.module('opiApp');

	var LoginController = function($scope, $location, $routeParams,
			authService, CONSTANTS) {

		var path = '/';

		$scope.credentials = {
			username : '',
			password : ''
		};
		$scope.returnMessage = '';
		$scope.isEmailValid = true;

		$scope.login = function(credentials) {

			$scope.returnMessage = "Logging in... Please, wait!";
			authService.login(credentials).then(function(status) {

				console.log('STATUS');
				console.log(status);
				if (parseInt(status) !== CONSTANTS.OK) {
					if (parseInt(status) === CONSTANTS.USER_IS_BLOCKED)
						$scope.returnMessage = 'Your account is currently blocked, contact your admin!';
					else
						$scope.returnMessage = 'Invalid login!';
					return;
				}

				if ($routeParams && $routeParams.redirect) {
					path = path + $routeParams.redirect;
				}

				$location.path(path);
			});
		};
	};

	LoginController.$inject = [ '$scope', '$location', '$routeParams',
			'authService', 'CONSTANTS' ];

	angular.module('opiApp').controller('LoginController', LoginController);
})();