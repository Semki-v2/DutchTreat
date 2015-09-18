'use strict';

angular.module("Authentication")

.controller("AuthenticationCtrl", function($scope, $rootScope, $location, $routeParams,AuthenticationService) {
	$rootScope.$path = $location.path.bind($location);

	$scope.login = function(credentials) {
		AuthenticationService.login({name:credentials.username,password:credentials.password});
	};
});