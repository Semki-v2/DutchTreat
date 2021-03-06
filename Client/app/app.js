"use strict";

angular.module("Eventos", ["Participants", "ngRoute", "Purchases", "ngResource","Authentication"]);
angular.module("Purchases", ["Participants"]);
angular.module("Transfers", ["Participants"]);
angular.module("Balance", ["Eventos"]);
angular.module("Authentication",[]);

// Declare app level module which depends on views, and components
angular.module("semki.DutchTreat", [
  "ngAnimate", "ngRoute","ngResource", "ui.bootstrap", "ngTagsInput",
  "angular-loading-bar", "isteven-multi-select","angular-growl",
  "Eventos", "Purchases", "Transfers", "Balance","Authentication"])

  .config(function($locationProvider, $routeProvider, $httpProvider,growlProvider) {

    $locationProvider.html5Mode(true);

    growlProvider.globalTimeToLive(5000);
    growlProvider.globalPosition('bottom-center');

    $routeProvider
      .when("/events", {
        templateUrl: "views/events/events-list.html",
        controller: "EventosCtrl"
      })
      .when("/events/:id/edit", {
      	templateUrl: "views/events/event-edit.html",
        controller: "EventoEditCtrl"
      })
      .when("/events/:id/addUser/:invateHash", {
        templateUrl: "views/events/event-edit.html",
        controller: "EventoUrlEditCtrl"
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
      .when("/events/:event_id/balance", {
        templateUrl: "views/balance/pariticipant-balance-view.html",
        controller: "ParticipantBalanceCtrl"
      })
      .when("/auth/login", {
        templateUrl: "views/auth/signin.html",
        controller: "AuthenticationCtrl"
      })
      .when("/auth/registration", {
        templateUrl: "views/auth/account-new.html",
        controller: "AccountNewCtrl"
      })
      .when("/auth/account/:account_id/edit", {
        templateUrl: "views/auth/account-edit.html",
        controller: "AccountEditCtrl"
      })
      .otherwise({
        redirectTo: "events/"
      });

    var interceptor = function ($q,$location,$rootScope,growl) {
      return {
        request: function ( config ) { 
            //console.log("run request");
            return config;
          },
        response: function ( response ) {
            //console.log("ger response");
            return response;
          },
        responseError: function ( response ) { 
           if (response.status === 401) {
                //console.log("Response 401");

                if ($rootScope.previousPage == null) {
                  $rootScope.previousPage = $location.$$path;
                  //console.log("previousPage = " + $rootScope.previousPage);
                }
                else {
                  //console.log("use old previousPage = " + $rootScope.previousPage); 
                }

                $location.url('/auth/login');
            }
            else if(response.status === 403)
            {
              growl.warning("доступ запрещен");
              $location.url('/auth/events'); 
            }
            else if((response.status >= 500)&&(response.status < 600))
            {
              console.log("ger error");
              growl.error("На сервере произошла ошибка");
            }
            else
            {
              // other errors
            }
            return $q.reject( response );
          }
        };
      };
    $httpProvider.interceptors.push(interceptor);
  }
);