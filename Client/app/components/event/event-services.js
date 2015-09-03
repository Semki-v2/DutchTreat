"use strict";

angular.module("Eventos")
	.factory("EventosRest", function($resource) {
		return $resource("/dutch-treat/app/api/eventos/:id", {id: "@id"});
	})
	.service("EventosService", function (ParticipantService, EventosRest,$http) {
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