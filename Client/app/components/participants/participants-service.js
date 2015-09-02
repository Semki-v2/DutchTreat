'use strict';

angular.module('Participants', [])
	.factory("ParticipantsRest", function($resource) {
		return $resource("/dutch-treat/app/api/participants/:id", {id: "@id"});
	})
	.service('ParticipantService', function (ParticipantsRest) {
		var participantsList =  [
			{id:1, name: "Вова", event_id: 1},
			{id:2, name: "Денис", event_id: 1},
			{id:3, name: "Макс", event_id: 1},
			{id:4, name: "Паха", event_id: 1},
			{id:5, name: "Ира", event_id: 1},
			{id:6, name: "Катя", event_id: 1},
			{id:7, name: "Наташа", event_id: 1},
			{id:8, name: "Маша", event_id: 1},
			{id:9, name: "ДЯ", event_id:2},
			{id:10, name: "Вова", event_id:2},
			{id:11, name: "ДК", event_id:2}
	      ];
		return {
			getParticipants : function (event_id) {
				//return participantsList;
				//.filter(function(participant) {return participant.event_id == event_id} );

				return ParticipantsRest.get({id:event_id});
			},
			addParticipant : function (participant) {
				var maxId = participantsList.reduce(function(a, b, c) { 
					return a > b.id ? a: b.id
				}, 0);
				participant.id = maxId+1;
				participantsList.push(participant);
			},
			updateParticipant : function ( participant ) {
				return participant;
			},
			getParticipant : function ( id ) {
				return participantsList.reduce(function(a, b, c) { 
					return b.id == id ? b: a
				}, {id : 0});
			},
			deleteParticipant : function ( participant ) {
				var index = participantsList.indexOf(participant);
				participantsList.splice(index, 1);
			}
		};
	});