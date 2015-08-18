"use strict";

angular.module("Purchases")

	.service("PurchaseService", function (ParticipantService) {
		var purchasesList =  [
	        {id: 1, event_id: 1, amount: 1000, description: "Бензин", buyer_id: 1, consumer_ids: [1,2,3,4,5,6,7,8] },
	        {id: 2, event_id: 1, amount: 1600, description: "Рыба", buyer_id: 2, consumer_ids: [1,2,4,8] },
	        {id: 3, event_id: 2, amount: 2500, description: "Пицца из перцев", buyer_id: 9, consumer_ids: [9,10,11] },
	      ];
		return {
			getPurchases : function (event_id) {
				var result = purchasesList.filter(function(purchase) {
					return purchase.event_id == event_id;
				});
				result = result.map(function(purchase) {
					return {id: purchase.id, 
						event_id: purchase.event_id, 
						amount: purchase.amount, 
						description: purchase.description,
						buyer: ParticipantService.getParticipant(purchase.buyer_id),
						consumers: purchase.consumer_ids.map(function(consumer_id) {
							return ParticipantService.getParticipant(consumer_id);
						})
					};
				});
				return result;
			},
			addPurchase : function (purchase) {
				var maxId = purchasesList.reduce(function(a, b) { 
					return a > b.id ? a: b.id;
				}, 0);
				var storedPurchase = {
					id: maxId+1,
					event_id: purchase.event_id,
					amount: purchase.amount,
					description: purchase.description,
					buyer_id: purchase.buyer.id,
					consumer_ids: purchase.consumers.map(function(participant) {
						return participant.id;
					})
				};
				purchasesList.push(storedPurchase);
			},
			updatePurchase : function ( purchase ) {
				var storedPurchase = purchasesList.reduce(function(a, b) { 
					return b.id == purchase.id ? b: a;
				}, {id : 0});
				
				storedPurchase.event_id = purchase.event_id;
				storedPurchase.amount = purchase.amount;
				storedPurchase.description = purchase.description;
				storedPurchase.buyer_id = purchase.buyer.id;
				storedPurchase.consumer_ids = purchase.consumers.map(function(participant) {
						return participant.id;
					});
			},
			getPurchase : function ( id ) {
				var purchase = purchasesList.reduce(function(a, b) { 
					return b.id == id ? b: a;
				}, {id : 0});

				var result = {id: purchase.id, 
						event_id: purchase.event_id, 
						amount: purchase.amount, 
						description: purchase.description,
						buyer: ParticipantService.getParticipant(purchase.buyer_id),
						consumers: purchase.consumer_ids.map(function(consumer_id) {
							return ParticipantService.getParticipant(consumer_id);
						})
					};
				return result;
			},
			deletePurchase : function ( purchase ) {
				var storedPurchase = purchasesList.reduce(function(a, b) { 
					return b.id == purchase.id ? b: a;
				}, {id : 0});
				var index = purchasesList.indexOf(storedPurchase);
				purchasesList.splice(index, 1);
			}
		};
	});