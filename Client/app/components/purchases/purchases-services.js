"use strict";

angular.module("Purchases")

	.factory("PurchasesRest", function($resource) {
		return $resource("/dutch-treat/app/api/purchases/:id", {id: "@id"});
	})

	.service("PurchaseService", function (PurchasesRest) {
		return {
			getPurchases : function (event_id) {
				return PurchasesRest.query({event_id: event_id})
			},
			addPurchase : function (purchase) {
				return purchase.$save();
			},
			updatePurchase : function ( purchase ) {
				return purchase.$save();
			},
			createPurchase: function(evento) {
				var purchase = new PurchasesRest();
				purchase.event_id = evento.id;
				purchase.consumers = evento.participants;
				return purchase;
			},
			getPurchase : function ( id ) {
				return PurchasesRest.get({id: id});
			},
			deletePurchase : function ( purchase ) {
				return purchase.$delete();
			}
		};
	});