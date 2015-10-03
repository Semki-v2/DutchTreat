'use strict';

angular.module("Authentication")

	.service("AuthenticationService", function ($http,$rootScope,$location,$q) {
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
						resolve(currentService.getCurrentUser());
					}
				});
			}
		};
	});