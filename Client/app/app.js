'use strict';

// Declare app level module which depends on views, and components
angular.module('semki.DutchTreat', [
  'ngAnimate', 'ngRoute','ngResource', 'ui.bootstrap',
  'EventControllers'])

  .config(function($locationProvider, $routeProvider) {

    $locationProvider.html5Mode(true);

    $routeProvider
      .when('/events', {
        templateUrl: 'views/events/events-list.html',
        controller: 'EventosCtrl'
      })
      .when('/events/wow', {
      	templateUrl: 'views/events/events-list.html',
        controller: 'EventosCtrl'
      })
      .when('/events/new', {
      	templateUrl: 'views/events/event-new.html',
      	controller: 'EventoNewCtrl'
      })
      .otherwise({
        redirectTo: 'events/'
      });

  });