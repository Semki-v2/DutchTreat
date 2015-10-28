"use strict";

angular.module("Eventos")
	.factory("EventosRest", function($resource) {
		return $resource("/dutch-treat/app/api/eventos/:id", {id: "@id"});
	})
	.factory("EventosCreateInvate", function($resource) {
		return $resource("/dutch-treat/app/api/eventos/:id/createInvate", {id: "@id"});
	})
	.factory("EventosAddInvateRest", function($resource) {
		return $resource("/dutch-treat/app/api/eventos/:id/addUser/:invate", {id: "@id",invate:"@invate"});
	})
	.service("EventosService", function (ParticipantService, EventosRest,EventosCreateInvate,EventosAddInvateRest,$http) {
		return {
			getEventos : function () {
				return EventosRest.query();
			},
			addEvento : function (evento) {
				return evento.$save();
			},
			updateEvento : function ( evento ) {
				return evento.$save();
			},
			invateUser : function ( evento ) {
				return EventosCreateInvate.get({id : evento.id});
			},
			addUser : function ( eventId,invateHash ) {
				return EventosAddInvateRest.get({id : eventId,invate:invateHash});
			},
			createEvento : function () {
				return new EventosRest();
			},
			getEvento : function ( id ) {
				return EventosRest.get({id : id});
			},
			deleteEvento : function ( evento ) {
				return evento.$delete();
			}
		};
	});