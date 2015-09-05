'use strict';

angular.module("Balance")

.controller("ParticipantBalanceCtrl", function($scope, $rootScope, $location, $routeParams, EventosService, BalanceService) {
	$rootScope.$path = $location.path.bind($location);

	var event_id = Number($routeParams.event_id);

	$scope.evento = EventosService.getEvento(event_id);
	
	$scope.changeTargetParticipant = function() {
		BalanceService.getCalculation($scope.target_participant).then(function(response_balance) {
			$scope.calculation = response_balance.data;
		});
	};
});