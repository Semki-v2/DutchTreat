'use strict';

angular.module('EventControllers', ['EventServices', 'ngRoute'])

  .controller('EventosCtrl', function($scope, $rootScope, $location, $route, EventosService) {
  	console.log("ololo");
    $rootScope.$path = $location.path.bind($location);
    $scope.eventos = EventosService.getEventos();
  })

  .controller('EventoNewCtrl', function($scope, $rootScope, $location, $route, EventosService) {
  	console.log("ololo2");
    $rootScope.$path = $location.path.bind($location);
    $scope.evento = {};
    $scope.addEvento = function() {
    	EventosService.addEvento($scope.evento);
    	$location.path($scope.baseUrl + '/events');
    };
  });