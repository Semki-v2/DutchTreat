'use strict';

angular.module("Authentication")
	.constant("AuthConst",{
		LOCATION_LOGIN : "/auth/login",
		LOCATION_REGISTRATION : "/auth/registration",
		LOCATION_MAIN : "/auth/events"
	})
	.service("AuthenticationService", function ($http,$rootScope,$location,$q,growl,AuthConst) {

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

		        	$rootScope.currentUser = null;

		        	$rootScope.$broadcast('Authentication:updated',null);  

		            if (($rootScope.previousPage != null)) {
		            	var redirectPath = AuthConst.LOCATION_MAIN;

		            	if(($rootScope.previousPage != AuthConst.LOCATION_LOGIN) &&
		            		($rootScope.previousPage != AuthConst.LOCATION_REGISTRATION))
		            	{
		            		redirectPath = $rootScope.previousPage;
		            	}


               			$location.path(redirectPath).replace();
               			$rootScope.previousPage = null;
              		}
              		else
              		{
              			$location.path(AuthConst.LOCATION_MAIN).replace();
              		}
		        }, function(credentials) {
		            //alert("error logging in");
		            growl.error("Ошибка входа. Проверьте пароль и попробуйте еще раз");
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

					$location.path(AuthConst.LOCATION_LOGIN).replace();

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
		        .then(function(response) {
		        	growl.success("пользователь успешно зарегистрирован");
		        }, function(response) {
		        	if (response.status == 400)
		        	{
		        		growl.error(response.data.Message);
		        	}
		            return $q.reject();
		        });
			}
			,
			update : function(account){
				return $http({
				    method: 'POST',
				    url: "/dutch-treat/app/auth/account/edit",
				    data: account
				}).then(function(response) {
		        		growl.success("пользователь успешно сохранен");
			        }, function(response) {
			        	if (response.status == 400)
			        	{
			        		growl.error(response.data.Message);
			        	}
			            return $q.reject();
			        });
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
			checkLogin : function(login){
				return $http({
							    method: 'GET',
							    url: "/dutch-treat/app/auth/checkuser",
							    params: {username:login}
							});	
			}
			,
			checkEmail : function(email){
				return $http({
							    method: 'GET',
							    url: "/dutch-treat/app/auth/checkemail",
							    params: {email:email}
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