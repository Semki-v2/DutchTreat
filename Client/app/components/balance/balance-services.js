'use strict';

angular.module("Balance")

	.service("BalanceService", function ($http) {
		return {
			getCalculation : function (pariticipant) {
				return $http.get("/dutch-treat/app/api/balance/by-participant/" + pariticipant.id);
			},
			getSummary : function (eventId) {
				return $http.get("/dutch-treat/app/api/balance/summary-by-event/" + eventId);
			}
		};
	});