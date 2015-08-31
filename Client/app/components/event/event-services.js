"use strict";

angular.module("Eventos")
	.factory("EventosRest", function($resource) {
		return $resource("/dutch-treat/app/api/eventos/:id", {id: "@id"});
	})
	.service("EventosService", function (ParticipantService, EventosRest,$http) {
		var eventosList =  [
	        {id: 1, name: "Байкал", startDate: "2015-07-19", finishDate: "2015-07-26", participants: [] },
	        {id: 2, name: "Хакатон", startDate: "2015-08-09", participants: [] }
	      ];
		return {
			getEventos : function () {
				return EventosRest.query();
			},
			addEvento : function (evento) {
				evento.$save();
			},
			updateEvento : function ( evento ) {
				// var oldParticipants = ParticipantService.getParticipants(evento.id);
				// oldParticipants.filter( function(oldParticipant) {
				// 	if (evento.participants.indexOf(oldParticipant) < 0 ) {
				// 		ParticipantService.deleteParticipant(oldParticipant);
				// 	}
				// });
				// evento.participants.map(function(participant) {
				// 	if (!participant.hasOwnProperty("id") || participant.id <= 0) {
				// 		participant.event_id = evento.id;
				// 		ParticipantService.addParticipant(participant);
				// 	}
				// });
				evento.$save();
			},
			createEvento : function () {
				return new EventosRest();
			},
			getEvento : function ( id ) {
				return EventosRest.get({id : id});
			},
			deleteEvento : function ( evento ) {
				evento.$delete();
			}
		};
	});