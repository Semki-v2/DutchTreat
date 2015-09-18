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
		};
	});