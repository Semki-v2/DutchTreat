"use strict";

angular.module("Eventos", ["Participants", "ngRoute", "Purchases", "ngResource"]);
angular.module("Purchases", ["Participants"]);
angular.module("Transfers", ["Participants"]);

// Declare app level module which depends on views, and components
angular.module("semki.DutchTreat", [
  "ngAnimate", "ngRoute","ngResource", "ui.bootstrap", "ngTagsInput",
  "Eventos", "Purchases", "Transfers"])

  .config(function($locationProvider, $routeProvider) {

    $locationProvider.html5Mode(true);

    $routeProvider
      .when("/events", {
        templateUrl: "views/events/events-list.html",
        controller: "EventosCtrl"
      })
      .when("/events/:id/edit", {
      	templateUrl: "views/events/event-edit.html",
        controller: "EventoEditCtrl"
      })
      .when("/events/new", {
      	templateUrl: "views/events/event-new.html",
      	controller: "EventoNewCtrl"
      })
      .when("/events/:event_id/purchases/new", {
      	templateUrl: "views/purchases/purchase-new.html",
      	controller: "PurchaseNewCtrl"
      })
      .when("/events/:event_id/purchases/:id/edit", {
      	templateUrl: "views/purchases/purchase-edit.html",
      	controller: "PurchaseEditCtrl"
      })
      .when("/events/:id", {
      	templateUrl: "views/events/event-view.html",
        controller: "EventoViewCtrl"
      })
      .when("/events/:event_id/transfers", {
      	templateUrl: "views/transfers/transfers-list.html",
      	controller: "TransfersCtrl"
      })
      .when("/events/:event_id/transfers/new", {
      	templateUrl: "views/transfers/transfer-new.html",
      	controller: "TransferNewCtrl"
      })
      .when("/events/:event_id/transfers/:id/edit", {
      	templateUrl: "views/transfers/transfer-edit.html",
      	controller: "TransferEditCtrl"
      })
      .otherwise({
        redirectTo: "events/"
      });

  });