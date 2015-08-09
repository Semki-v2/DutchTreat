'use strict';

angular.module('DutchTreat', ['ngAnimate', 'ngRoute','ngResource', 'ui.bootstrap'])

  .config(function($locationProvider, $routeProvider) {

    $locationProvider.html5Mode(true);

    $routeProvider
      .when('/events/', {
        templateUrl: 'views/events/events-list.html',
        controller: 'EventosCtrl'
      })
      .otherwise({
        redirectTo: '/events'
      });

  });