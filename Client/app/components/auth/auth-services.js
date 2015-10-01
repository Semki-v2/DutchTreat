'use strict';

angular.module("Authentication")

	.service("AuthenticationService", function ($http,$rootScope,$location) {
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

		            if ($rootScope.previousPage != null) {
               			$location.path($rootScope.previousPage).replace();
               			$rootScope.previousPage = null;
              		}
              		else
              		{
              			$location.path("/app/events").replace();
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
		        }, function(credentials) {
		        	$rootScope.currentUser = null;
					$rootScope.isAuthenticated = null;
		            alert("error logout");
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
		        	$rootScope.authenticated = true;
		            $location.path("/dutch-treat/app/auth/login").replace();
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

				if ($rootScope.currentUser!=null)
				{
					return $rootScope.currentUser;
				}
				else
				{
					$http({
					    method: 'GET',
					    url: "/dutch-treat/app/auth/current"
					})
			        .then(function(response) {
			        	$rootScope.currentUser = response.data; 
			        }, function() {
			            console.log("current user service error");
			        });

			        return null;
				}
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
		};
	});