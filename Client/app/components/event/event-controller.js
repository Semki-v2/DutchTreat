'use strict';

angular.module('EventControllers', ['EventServices', 'ngRoute'])

  .controller('EventosCtrl', function($scope, $rootScope, $location, EventosService) {
  	$rootScope.$path = $location.path.bind($location);
    $scope.eventos = EventosService.getEventos();
    $scope.deleteEvento = function ( evento ) {
    	EventosService.deleteEvento(evento);
    }
  })

  .controller('EventoNewCtrl', function($scope, $rootScope, $location, EventosService) {
  	$rootScope.$path = $location.path.bind($location);
    $scope.evento = {};
    $scope.addEvento = function() {
    	EventosService.addEvento($scope.evento);
    	$location.path($scope.baseUrl + '/events');
    };
  })

  .controller('EventoEditCtrl', function($scope, $rootScope, $location, $routeParams, EventosService) {
  	$rootScope.$path = $location.path.bind($location);
    $scope.evento = EventosService.getEvento($routeParams.id);
    $scope.editEvento = function() {
    	EventosService.updateEvento($scope.evento);
    	$location.path($scope.baseUrl + '/events');
    };
  });