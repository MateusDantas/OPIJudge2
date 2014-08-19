(function() {

    var opiApp = angular.module('opiApp');

    var authFactory = function($http, $rootScope) {

	var basePath = opiApp.CONSTANTS.BASE_PATH,
	factory = {
	    loginPath: opiApp.CONSTANTS.LOGIN_PATH,
	    logoutPath: opiApp.CONSTANTS.LOGOUT_PATH,
	    registerPath: opiApp.CONSTANTS.REGISTER_PATH,
	    user: {
		isAuthenticated: false,
		userToken: null,
		roles: null
	    }
	};
	
	factory.login = function (username_, password_) {
	    
	    return $http.post(basePath + factory.loginPath, {username: username_, password: password_}).then(
		function (results) {
		    var isLoggedIn = results.data.responseStatus == opiApp.CONSTANTS.OK;
		    var userToken = results.data.token;
		    setUserToken(userToken);
		    changeAuth(isLoggedIn);
		    return isLoggedIn;
		});
	};
	
	factory.logout = function(username_, token_) {
	    
	    return $http.post(basePath + factory.logoutPath, {username: username_, token: token_}).then(
		function (results) {

		    var isLoggedIn = results.data.responseStatus != opiApp.CONSTANTS_OK;
		    changeAuth(isLoggedIn);
		    if (!isLoggedIn)
			setUserToken(null);

		    return isLoggedIn;
		});
	};

	factory.register = function(name_, username_, email_, password_) {
	    
	    return $http.post(basePath + factory.registerPath, {name: name_, username: username_, email: email_, password: password_}).then(
		function (results) {
		    return results.data;
		});
	};

	factory.redirectToLogin = function() {
	    $rootScope.$broadcast('redirectToLogin', null);
	};
	
	function changeAuth(isLoggedIn) {
	    factory.user.isAuthenticated = isLoggedIn;
	    $rootScope.$broadcast('loginStatusChanged', isLoggedIn);
	}
	
	function setUserToken(userToken) {
	    factory.user.userToken = userToken;
	}
	
	return factory;
    };
    
    authFactory.$inject = ['$http', '$rootScope'];
    
    angular.module('opiApp').factory('authService', authFactory);
    
}());
