"use strict";

angular.module("Transfers")

.directive('transfersTable', function(TransferService, $modal, $templateCache) {
  return {
  	restrict: 'E',
  	scope: {
      eventId: '@'
    },
    link: function(scope) {
    	scope.$watch('eventId', function(newValue, oldValue) {
    		if (newValue != "") {
    			scope.transfers = TransferService.getTransfers(newValue);
    		}
    	});
		
		scope.askDeleteTransfer = function(transfer) {

			var modalInstance = $modal.open({
		      templateUrl: 'confirmDeleteTransfer.html',
		      controller: function ($scope, $modalInstance) {

				 $scope.transfer = transfer;

				  $scope.confimDelete = function (transfer) {
				  	TransferService.deleteTransfer(transfer).then(function() {
						scope.transfers = TransferService.getTransfers(scope.eventId);
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
    templateUrl: 'views/transfers/transfers-list.html'
  };
})