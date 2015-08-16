'use strict';

angular.module('EventServices', [])

	.service('EventosService', function () {
		var eventosList =  [
	        {id: 1, name: 'Байкал', startDate: '2015-07-19', finishDate: '2015-07-26'},
	        {id: 2, name: 'Хакатон', startDate: '2015-08-09'}
	      ];
		return {
			getEventos : function () {
				return eventosList;
			},
			addEvento : function (evento) {
				var maxId = eventosList.reduce(function(a, b, c) { 
					return a > b.id ? a: b.id
				}, 0);
				evento.id = maxId+1;
				eventosList.push(evento);
			},
			updateEvento : function ( evento ) {
				return evento;
			},
			getEvento : function ( id ) {
				return eventosList.reduce(function(a, b, c) { 
					return b.id == id ? b: a
				}, {id : 0});
			},
			deleteEvento : function ( evento ) {
				var index = eventosList.indexOf(evento);
				eventosList.splice(index, 1);
			}
		};
	});