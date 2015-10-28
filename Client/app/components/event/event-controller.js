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

.controller('EventoNewCtrl', function($scope, $rootScope, $location,$http, EventosService,AuthenticationService) {
	$rootScope.$path = $location.path.bind($location);
	$scope.evento = EventosService.createEvento();

	AuthenticationService.getAccountList().then(function(response){
		$scope.accounts = response.data;

		if ($rootScope.currentUser != null) {
			$scope.accounts.map(function(account) {
				account.selected = ($rootScope.currentUser.id == account.id);
			});
		}
	});

	$scope.multiselectLocalization = {
		selectAll       : "Все",
	    selectNone      : "Очистить",
	    reset           : "Сбросить",
	    search          : "Найти...",
	    nothingSelected : "Пусто"
	};

	$scope.addEvento = function() {
		EventosService.addEvento($scope.evento).then(function() {
			$location.path('events');
		});		
	};
})

.controller('EventoEditCtrl', function($scope, $rootScope, $location, $routeParams, EventosService,AuthenticationService) {
	$rootScope.$path = $location.path.bind($location);
	$scope.evento = EventosService.getEvento($routeParams.id);

	$scope.$watch('evento.invate', function(newValue, oldValue) {
		var currentBasePath = $location.absUrl().split("edit")[0];

		$scope.inviteHash = currentBasePath+"addUser/"+$scope.evento.invate;
	});

	AuthenticationService.getAccountList().then(function(response){
		$scope.accounts = response.data;

		$scope.evento.$promise.then(function(value) {
			var accessAccountsIds = $scope.evento.accessAccounts.map(function(account) {
				return account.id;
			});

			$scope.accounts.map(function(account) {
				account.selected = (accessAccountsIds.indexOf(account.id) >=0 );
			});
		});
	});

	$scope.multiselectLocalization = {
		selectAll       : "Все",
	    selectNone      : "Очистить",
	    reset           : "Сбросить",
	    search          : "Найти...",
	    nothingSelected : "Пусто"
	};

	$scope.editEvento = function() {
		EventosService.updateEvento($scope.evento).then(function() {
			$location.path('events');
		});		
	};

	$scope.invateUser = function() {
		EventosService.invateUser($scope.evento).$promise.then(function(evento) {
			$scope.evento = evento;
		});		
	};
})
.controller('EventoUrlEditCtrl', function($scope, $rootScope, $location, $routeParams,growl, EventosService,AuthenticationService) {
	var invite = $routeParams.invateHash;

	EventosService.addUser($routeParams.id,$routeParams.invateHash).$promise.then(function(){
		growl.success("пользователь успешно добавлен к событию");
		$location.path("/auth/events").replace();
	},function(){
		growl.error("ошибка при добавлении поьзователя");
		$location.path("/auth/events").replace();
	});
})
.controller('EventoViewCtrl', function($scope, $rootScope, $location, $routeParams, EventosService, PurchaseService) {
	$rootScope.$path = $location.path.bind($location);
	$scope.evento = EventosService.getEvento($routeParams.id);
});