'use strict';

angular.module("Authentication")

	.service("AuthenticationService", function ($http) {
		return {
			signin : function (login,password) {
				$http.post("/app/auth/signin");
			}
		};
	});