"use strict";

angular.module("Purchases")

.directive('purchaseTable', function(PurchaseService, $modal, $templateCache) {
  return {
  	restrict: 'E',
  	scope: {
      eventId: '@'
    },
    link: function(scope) {
    	scope.$watch('eventId', function(newValue, oldValue) {
    		if (newValue != "") {
    			scope.purchases = PurchaseService.getPurchases(newValue);
    		}
    	});
		
		scope.askDeletePurchase = function(purchase) {

			var modalInstance = $modal.open({
		      templateUrl: 'confirmDeletePurchase.html',
		      controller: function ($scope, $modalInstance) {

				 $scope.purchase = purchase;

				  $scope.confimDelete = function (purchase) {
				  	PurchaseService.deletePurchase(purchase).then(function() {
						scope.purchases = PurchaseService.getPurchases(scope.eventId);
						$modalInstance.close();
					});
				    
				  };

				  $scope.cancelDelete = function () {
				    $modalInstance.dismiss('cancel');
				  };
				}
		      
		    });
		};
	},
    templateUrl: 'views/purchases/purchases-list.html'
  };
})