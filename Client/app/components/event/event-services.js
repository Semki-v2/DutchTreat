"use strict";

angular.module("Eventos")

	.service("EventosService", function (ParticipantService) {
		var eventosList =  [
	        {id: 1, name: "Байкал", startDate: "2015-07-19", finishDate: "2015-07-26", participants: [] },
	        {id: 2, name: "Хакатон", startDate: "2015-08-09", participants: [] }
	      ];
		return {
			getEventos : function () {
				return eventosList;
			},
			addEvento : function (evento) {
				var maxId = eventosList.reduce(function(a, b) { 
					return a > b.id ? a: b.id;
				}, 0);
				evento.id = maxId+1;
				eventosList.push(evento);
				evento.participants.map(function(participant) {
					participant.event_id = evento.id;
					ParticipantService.addParticipant(participant);
				});
			},
			updateEvento : function ( evento ) {
				var oldParticipants = ParticipantService.getParticipants(evento.id);
				oldParticipants.filter( function(oldParticipant) {
					if (evento.participants.indexOf(oldParticipant) < 0 ) {
						ParticipantService.deleteParticipant(oldParticipant);
					}
				});
				evento.participants.map(function(participant) {
					if (!participant.hasOwnProperty("id") || participant.id <= 0) {
						participant.event_id = evento.id;
						ParticipantService.addParticipant(participant);
					}
				});
				return evento;
			},
			getEvento : function ( id ) {
				var evento = eventosList.reduce(function(a, b) { 
					return b.id == id ? b: a;
				}, {id : 0});
				evento.participants = ParticipantService.getParticipants(evento.id);
				return evento;
			},
			deleteEvento : function ( evento ) {
				var index = eventosList.indexOf(evento);
				evento.participants.map(function(participant) {
					ParticipantService.deleteParticipant(participant.id);
				});
				eventosList.splice(index, 1);
			}
		};
	});