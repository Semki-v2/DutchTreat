"use strict";

angular.module("Balance")

.directive('summaryBalance', function(BalanceService, $modal, $templateCache) {
  return {
  	restrict: 'E',
  	scope: {
      eventId: '@'
    },
    link: function(scope) {
    	scope.$watch('eventId', function(newValue, oldValue) {
    		if (newValue != "") {
    			BalanceService.getSummary(newValue).then(function(responseDTO) {
    				scope.balanceRows = responseDTO.data.balanceRows;
    			})
    		}
    	});
	},
    templateUrl: 'views/balance/balance-summary-table.html'
  };
})