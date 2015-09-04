"use strict";

angular.module("Transfers")
	
	.factory("TransfersRest", function($resource) {
		return $resource("/dutch-treat/app/api/transfers/:id", {id: "@id"});
	})

	.service("TransferService", function (TransfersRest) {
		return {
			getTransfers : function (event_id) {
				return TransfersRest.query({event_id: event_id});
			},
			addTransfer : function (transfer) {
				return transfer.$save();
			},
			updateTransfer : function ( transfer ) {
				return transfer.$save();
			},
			createTransfer : function (event_id) {
				var transfer = new TransfersRest();
				transfer.event_id = event_id;
				return transfer;
			},
			getTransfer : function ( id ) {
				return TransfersRest.get({id : id});
			},

			deleteTransfer : function ( transfer ) {
				return transfer.$delete();
			}
		};
	});