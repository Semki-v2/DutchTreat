'use strict';

angular.module("semki.DutchTreat")

.controller("MainCtrl", function($scope, $rootScope, $location, $routeParams,AuthenticationService) {

	function checkAuth()
	{
		AuthenticationService.isAuthenticated().then(function(authData){
			$scope.isAuthenticated = authData.isAuthenticated;
			$scope.currentUser = authData.currentUser;
		});
	}

	checkAuth();

	$scope.$on('Authentication:updated', function(event,data) {
	    checkAuth();
   	});

	$scope.login = function() {
		
	};

	$scope.logout = function() {
		AuthenticationService.logout();
	};
})