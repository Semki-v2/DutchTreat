'use strict';

angular.module("Eventos")

.controller('EventosCtrl', function($scope, $rootScope, $location,$http,EventosService) {
	$rootScope.$path = $location.path.bind($location);
	$scope.eventos = EventosService.getEventos();
	$scope.deleteEvento = function(evento) {
		EventosService.deleteEvento(evento).then(function() {
			$scope.eventos = EventosService.getEventos();
		});
	}
})

.controller('EventoNewCtrl', function($scope, $rootScope, $location,$http, EventosService) {
	$rootScope.$path = $location.path.bind($location);
	$scope.evento = EventosService.createEvento();
	$scope.addEvento = function() {
		EventosService.addEvento($scope.evento).then(function() {
			$location.path('events');
		});		
	};
})

.controller('EventoEditCtrl', function($scope, $rootScope, $location, $routeParams, EventosService) {
	$rootScope.$path = $location.path.bind($location);
	$scope.evento = EventosService.getEvento($routeParams.id);
	$scope.editEvento = function() {
		EventosService.updateEvento($scope.evento).then(function() {
			$location.path('events');
		});		
	};
})

.controller('EventoViewCtrl', function($scope, $rootScope, $location, $routeParams, EventosService, PurchaseService) {
	$rootScope.$path = $location.path.bind($location);
	$scope.evento = EventosService.getEvento($routeParams.id);
	$scope.purchases = PurchaseService.getPurchases($routeParams.id);

	$scope.deletePurchase = function(purchase) {
		PurchaseService.deletePurchase(purchase);
		$scope.purchases = PurchaseService.getPurchases($routeParams.id);
	}
});