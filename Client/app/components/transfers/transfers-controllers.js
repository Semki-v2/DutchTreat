"use strict";

angular.module("Transfers")

.controller("TransfersCtrl", function($scope, $rootScope, $location, $routeParams, EventosService, TransferService) {
	$rootScope.$path = $location.path.bind($location);
	$scope.evento = EventosService.getEvento($routeParams.event_id);
	$scope.transfers = TransferService.getTransfers($routeParams.event_id);

	$scope.deleteTransfer = function(transfer) {
		TransferService.deleteTransfer(transfer).then(function() {
			$scope.transfers = TransferService.getTransfers($routeParams.event_id);
		});
	};
})

.controller("TransferNewCtrl", function($scope, $rootScope, $location, $routeParams, EventosService, TransferService) {
	$rootScope.$path = $location.path.bind($location);

	var event_id = Number($routeParams.event_id);

	$scope.evento = EventosService.getEvento(event_id);

	$scope.transfer = TransferService.createTransfer(event_id);
	$scope.addTransfer = function() {
		TransferService.addTransfer($scope.transfer).then(function() {
			$location.path("events/" + event_id + "/transfers");
		});
	};
})

.controller("TransferEditCtrl", function($scope, $rootScope, $location, $routeParams, EventosService, TransferService) {
	$rootScope.$path = $location.path.bind($location);

	var event_id = Number($routeParams.event_id);

	$scope.transfer = TransferService.getTransfer($routeParams.id);

	$scope.evento = EventosService.getEvento(event_id);

	$scope.editTransfer = function() {
		TransferService.updateTransfer($scope.transfer).then(function() {
			$location.path("events/" + event_id + "/transfers");
		});
	};
});