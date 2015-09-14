"use strict";

angular.module("Purchases")

.directive('purchaseTable', function(PurchaseService) {
  return {
  	restrict: 'E',
  	scope: {
      eventId: '@'
    },
    link: function(scope) {
		scope.purchases = PurchaseService.getPurchases(scope.eventId);

		scope.deletePurchase = function(purchase) {
				PurchaseService.deletePurchase(purchase).then(function() {
					scope.purchases = PurchaseService.getPurchases($routeParams.id);
				});
		}
	},
    templateUrl: 'views/purchases/purchases-list.html'
  };
})