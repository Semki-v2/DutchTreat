'use strict';

angular.module("semki.DutchTreat")

.controller("MainCtrl", function($scope, $rootScope, $location, $routeParams,AuthenticationService) {

	/*$rootScope.$watch('currentUser',function(){
		console.log("curuser changed");
		$scope.currentUser = AuthenticationService.getCurrentUser(); 


	});

	$rootScope.$watch('isAuthenticated',function(){
		console.log("isAuth changed");
		$scope.isAuthenticated = $rootScope.isAuthenticated;

		if($scope.isAuthenticated)
		{
			$scope.currentUser = AuthenticationService.getCurrentUser();
		} 		
	});*/

	AuthenticationService.isAuthenticated().then(function(authData){
		$scope.isAuthenticated = authData.isAuthenticated;
		$scope.currentUser = authData.currentUser;
	});


	$scope.login = function() {
		
	};

	$scope.logout = function() {
		AuthenticationService.logout()
		$rootScope.currentUser = null;
		$rootScope.isAuthenticated = null;
	};
})