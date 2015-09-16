'use strict';

angular.module("Authentication")

	.service("AuthenticationService", function ($http) {
		return {
			login : function (credentials) {
				return $http.post("/dutch-treat/app/auth/login", "username=" + credentials.name + "&password=" + credentials.password, {
		            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
		        }).then(function(credentials) {
		            alert("login successful");
		            localStorage.setItem("session", {});
		        }, function(credentials) {
		            alert("error logging in");
		        });
			}
		};
	});