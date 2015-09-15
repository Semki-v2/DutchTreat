'use strict';

angular.module("Authentication")

.controller("AuthenticationCtrl", function($scope, $rootScope, $location, $routeParams,AuthenticationService) {
	$rootScope.$path = $location.path.bind($location);

	
	
	$scope.signin = function() {
		AuthenticationService.signin(12,451);
	};
});