'use strict';

angular.module("Eventos")

.controller('EventosCtrl', function($scope, $rootScope, $location, EventosService) {
	$rootScope.$path = $location.path.bind($location);
	$scope.eventos = EventosService.getEventos();
	$scope.deleteEvento = function(evento) {
		EventosService.deleteEvento(evento);
	}
})

.controller('EventoNewCtrl', function($scope, $rootScope, $location, EventosService) {
	$rootScope.$path = $location.path.bind($location);
	$scope.evento = {
		participants: []
	};
	$scope.addEvento = function() {
		EventosService.addEvento($scope.evento);
		$location.path('events');
	};
})

.controller('EventoEditCtrl', function($scope, $rootScope, $location, $routeParams, EventosService) {
	$rootScope.$path = $location.path.bind($location);
	$scope.evento = EventosService.getEvento($routeParams.id);
	$scope.editEvento = function() {
		EventosService.updateEvento($scope.evento);
		$location.path('events');
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