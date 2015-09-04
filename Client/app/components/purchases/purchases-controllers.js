"use strict";

angular.module("Purchases")

.controller('PurchaseNewCtrl', function($scope, $rootScope, $location, $routeParams, $q, EventosService, PurchaseService) {
	$rootScope.$path = $location.path.bind($location);

	var event_id = Number($routeParams.event_id);

	$scope.evento = EventosService.getEvento(event_id);
	$scope.evento.$promise.then(function() {
		$scope.purchase = PurchaseService.createPurchase($scope.evento);
	});
	
	$scope.addPurchase = function() {
		PurchaseService.addPurchase($scope.purchase).then(function() {
			$location.path('events/' + event_id);
		});
	};
})

.controller('PurchaseEditCtrl', function($scope, $rootScope, $location, $routeParams, EventosService, PurchaseService) {
	$rootScope.$path = $location.path.bind($location);

	var event_id = Number($routeParams.event_id);

	$scope.purchase = PurchaseService.getPurchase($routeParams.id);

	$scope.evento = EventosService.getEvento(event_id)

	$scope.editPurchase = function() {
		PurchaseService.updatePurchase($scope.purchase).then(function() {
			$location.path('events/' + event_id);
		});
	};
});