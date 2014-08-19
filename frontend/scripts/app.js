var opiApp = angular.module('opiApp', ['ngRoute']);

opiApp.config(function($routeProvider) {

    $routeProvider
	.when('/', {
	    templateUrl : 'pages/home.html',
	    controller : 'mainController'
	});

});

opiApp.controller('mainController', function($scope) {
    $scope.message = 'heladssdasdasadlo';
});
