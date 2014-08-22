/**
 * 
 */
(function() {

	var opiModule = angular.module('opiApp');

	opiModule.factory('opiHttpInterceptor', [ '$q', '$rootScope', '$timeout',
			function($q, $rootScope, $timeout) {

				return {
					request : function(config) {
						return config;
					},
					requestError : function(request) {
						return $q.reject(request);
					},
					response : function(response) {
						return response;
					},
					responseError : function(response) {

						if (response.status === 401) {
							console.log('broadcasting');
							$rootScope.$broadcast("deleteUserData");
						}
						return $q.reject(response);
					}
				};
			} ]);

	opiModule.config(function($httpProvider) {
		$httpProvider.interceptors.push('opiHttpInterceptor');
	});

}());