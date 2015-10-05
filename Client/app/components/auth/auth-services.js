'use strict';

angular.module("Authentication")

	.service("AuthenticationService", function ($http,$rootScope,$location,$q) {

		var LOCATION_LOGIN = "/auth/login";
		var LOCATION_REGISTRATION = "/auth/registration";
		var LOCATION_MAIN = "/auth/events";

		return {
			login : function (credentials) {
				return $http({
				    method: 'POST',
				    url: "/dutch-treat/app/auth/login",
				    headers: {'Content-Type': 'application/x-www-form-urlencoded'},
				    transformRequest: function(obj) {
				        var str = [];
				        for(var p in obj)
				        str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
				        return str.join("&");
				    },
				    data: {username: credentials.name, password: credentials.password}
				})
		        .then(function(credentials) {

		        	$rootScope.isAuthenticated = true;

		        	$rootScope.$broadcast('Authentication:updated',null);  

		            if (($rootScope.previousPage != null)) {
		            	var redirectPath = LOCATION_MAIN;

		            	if(($rootScope.previousPage != LOCATION_LOGIN) &&
		            		($rootScope.previousPage != LOCATION_REGISTRATION))
		            	{
		            		redirectPath = $rootScope.previousPage;
		            	}


               			$location.path(redirectPath).replace();
               			$rootScope.previousPage = null;
              		}
              		else
              		{
              			$location.path(LOCATION_MAIN).replace();
              		}
		        }, function(credentials) {
		            alert("error logging in");
		        });
			}
			,
			logout : function () {
				return $http({
				    method: 'POST',
				    url: "/dutch-treat/app/auth/logout",
				    headers: {'Content-Type': 'application/x-www-form-urlencoded'},
				})
		        .then(function(credentials) {
		        	$rootScope.currentUser = null;
					$rootScope.isAuthenticated = null;
					$rootScope.$broadcast('Authentication:updated',null);

					$location.path(LOCATION_LOGIN).replace();

		        }, function(credentials) {
		        	$rootScope.currentUser = null;
					$rootScope.isAuthenticated = null;
		            //alert("error logout");
		        });
			}
			,
			signup : function(account){
				return $http({
				    method: 'POST',
				    url: "/dutch-treat/app/auth/registration",
				    data: account
				})
		        .then(function(account) {
		        	
		        }, function(account) {
		            alert("error ");
		        });
			}
			,
			update : function(account){
				return $http({
				    method: 'POST',
				    url: "/dutch-treat/app/auth/account/edit",
				    data: account
				})
			}
			,
			getCurrentUser : function(){
				return	$http({
					    method: 'GET',
					    url: "/dutch-treat/app/auth/current"
					})
			        .then(function(response) {
			        	$rootScope.currentUser = response.data;
			        	$rootScope.authenticated = true;
						return {currentUser:$rootScope.currentUser,isAuthenticated:$rootScope.authenticated};		        	 
			        }, function() {
			            console.log("current user service error");
			        });
			}
			,
			getAccountById : function(accId){
				return $http({
						    method: 'GET',
						    url: "/dutch-treat/app/auth/account/"+accId
						});
			}
			,
			getAccountList : function(){
				return $http({
						    method: 'GET',
						    url: "/dutch-treat/app/auth/account"
						});
			}
			,
			isAuthenticated : function(){
				var currentService = this;
				return $q(function(resolve,reject){
					if($rootScope.currentUser!=null)
					{
						resolve({currentUser:$rootScope.currentUser,isAuthenticated:$rootScope.authenticated});
					}
					else if($rootScope.currentUser==null)
					{
						currentService.getCurrentUser().then(function(){
							resolve({currentUser:$rootScope.currentUser,isAuthenticated:$rootScope.authenticated});
						});
					}
				});
			}
		};
	});