"use strict";

angular.module("Transfers")

	.service("TransferService", function (ParticipantService) {
		var transfersList =  [
	        {id: 1, event_id: 1, amount: 1000, sender_id: 1, receiver_id: 2 },
	        {id: 2, event_id: 1, amount: 1600, sender_id: 2, receiver_id: 3 },
	        {id: 3, event_id: 2, amount: 2500, sender_id: 10, receiver_id: 11 }
	      ];

	    var getTransfer = function ( id ) {
				var transfer = transfersList.reduce(function(a, b) { 
					return b.id == id ? b: a;
				}, {id : 0});

				var result = {id: transfer.id, 
						event_id: transfer.event_id, 
						amount: transfer.amount, 
						sender: ParticipantService.getParticipant(transfer.sender_id),
						receiver: ParticipantService.getParticipant(transfer.receiver_id)
					};
				return result;
			};
		return {
			getTransfers : function (event_id) {
				var result = transfersList.filter(function(transfer) {
					return transfer.event_id == event_id;
				});
				result = result.map(function(transfer) {
					return getTransfer(transfer.id);
				});
				return result;
			},
			addTransfer : function (transfer) {
				var maxId = transfersList.reduce(function(a, b) { 
					return a > b.id ? a: b.id;
				}, 0);
				var storedTransfer = {
					id: maxId+1,
					event_id: transfer.event_id,
					amount: transfer.amount,
					sender_id: transfer.sender.id,
					receiver_id: transfer.receiver.id,
				};
				transfersList.push(storedTransfer);
			},
			updateTransfer : function ( transfer ) {
				var storedTransfer = transfersList.reduce(function(a, b) { 
					return b.id == transfer.id ? b: a;
				}, {id : 0});
				
				storedTransfer.event_id = transfer.event_id;
				storedTransfer.amount = transfer.amount;
				storedTransfer.sender_id = transfer.sender.id;
				storedTransfer.receiver_id = transfer.receiver.id;
			},
			getTransfer : getTransfer,

			deleteTransfer : function ( transfer ) {
				var storedTransfer = transfersList.reduce(function(a, b) { 
					return b.id == transfer.id ? b: a;
				}, {id : 0});
				var index = transfersList.indexOf(storedTransfer);
				transfersList.splice(index, 1);
			}
		};
	});