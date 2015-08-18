"use strict";

angular.module("Purchases")

.controller('PurchaseNewCtrl', function($scope, $rootScope, $location, $routeParams, EventosService, ParticipantService, PurchaseService) {
	$rootScope.$path = $location.path.bind($location);

	var event_id = Number($routeParams.event_id);

	$scope.evento = EventosService.getEvento(event_id)

	$scope.participants = ParticipantService.getParticipants(event_id);

	$scope.purchase = {
		event_id: event_id,
		consumers: ParticipantService.getParticipants(event_id)
	};
	$scope.addPurchase = function() {
		PurchaseService.addPurchase($scope.purchase);
		$location.path('/events/' + event_id);
	};
})

.controller('PurchaseEditCtrl', function($scope, $rootScope, $location, $routeParams, EventosService, ParticipantService, PurchaseService) {
	$rootScope.$path = $location.path.bind($location);

	var event_id = Number($routeParams.event_id);

	$scope.purchase = PurchaseService.getPurchase($routeParams.id);

	$scope.evento = EventosService.getEvento(event_id)

	$scope.participants = ParticipantService.getParticipants(event_id);

	$scope.editPurchase = function() {
		PurchaseService.updatePurchase($scope.purchase);
		$location.path('/events/' + event_id);
	};
});