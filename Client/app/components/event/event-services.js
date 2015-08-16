'use strict';

angular.module('EventServices', [])

	.service('EventosService', function () {
		var eventosList =  [
	        {name: 'Байкал', startDate: '2015-07-19', finishDate: '2015-07-26'},
	        {name: 'Хакатон', startDate: '2015-08-09'}
	      ];
		return {
			getEventos : function () {
				return eventosList;
			},
			addEvento : function (evento) {
				eventosList.push(evento);
			}
		};
	});