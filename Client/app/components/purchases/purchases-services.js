"use strict";

angular.module("PurchasesModule", [])

	.service("PurchaseService", function () {
		var purchasesList =  [
	        {id: 1, event_id: 1, amount: 1000, description: "Бензин", buyer_id: 1, consumers: [1,2,3,4,5,6,7,8] },
	        {id: 2, event_id: 1, amount: 1600, description: "Рыба", buyer_id: 2, consumers: [1,2,4,8] },
	        {id: 3, event_id: 2, amount: 2500, description: "Пицца из перцев", buyer_id: 9, consumers: [9,10,11] },
	      ];
		return {
			getPurchases : function (event_id) {
				return purchasesList.filter(function(purchase) {
					return purchase.event_id === event_id;
				});
			},
			addPurchase : function (purchase) {
				var maxId = purchasesList.reduce(function(a, b) { 
					return a > b.id ? a: b.id;
				}, 0);
				purchase.id = maxId+1;
				purchasesList.push(purchase);
			},
			updatePurchase : function ( purchase ) {
				return purchase;
			},
			getPurchase : function ( id ) {
				return purchasesList.reduce(function(a, b) { 
					return b.id === id ? b: a;
				}, {id : 0});
			},
			deletePurchase : function ( purchase ) {
				var index = purchasesList.indexOf(purchase);
				purchasesList.splice(index, 1);
			}
		};
	});