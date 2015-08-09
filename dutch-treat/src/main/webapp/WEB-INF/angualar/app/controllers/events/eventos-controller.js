'use strict';

angular.module('DutchTreat')

  .controller('EventosCtrl', function($scope, $rootScope, $location, $route) {
    $rootScope.$path = $location.path.bind($location);
    $scope.eventos = [
        {name: "Байкал", startDate: "2015-07-19", finishDate: "2015-07-26"},
        {name: "Хакатон", startDate: "2015-08-09"}
      ];

  })