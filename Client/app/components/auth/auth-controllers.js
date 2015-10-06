'use strict';

angular.module("Authentication")

.controller("AuthenticationCtrl", function($scope, $rootScope, $location, $routeParams,AuthenticationService) {
	$rootScope.$path = $location.path.bind($location);

	$scope.login = function(credentials) {
		AuthenticationService.login({name:credentials.username,password:credentials.password});
	};
})

.controller("AccountNewCtrl", function($scope, $rootScope, $location, $routeParams,AuthenticationService) {
	$rootScope.$path = $location.path.bind($location);

	$scope.account = {};

	$scope.Creation = true;

	$scope.signup = function() {
		AuthenticationService.signup($scope.account).then(function(){
			AuthenticationService.login({name:$scope.account.user_login,password:$scope.account.user_password});
		});
	};
})

.controller("AccountEditCtrl", function($scope, $rootScope, $location, $routeParams,growl,AuthenticationService) {
	$rootScope.$path = $location.path.bind($location);

	var account_id = Number($routeParams.account_id);

	AuthenticationService.getAccountById(account_id).then(function(response){
		$scope.account = response.data;
	});

	$scope.Edit = true;

	$scope.update = function() {

		AuthenticationService.update($scope.account).then(function(){
			growl.success("пользователь успешно сохранен");
		});
	};
});