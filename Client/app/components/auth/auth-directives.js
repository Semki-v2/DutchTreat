'use strict';

angular.module("Authentication")
.directive('userValidation', ['$q','AuthenticationService', function($q,AuthenticationService){
	return {
		require: 'ngModel',
		restrict : 'A',
		link: function($scope, iElm, iAttrs, controller) {
			controller.$asyncValidators.userValidation = function (modelValue, viewValue) {
		        return $q(function (resolve, reject) {
	          	    AuthenticationService.checkLogin(viewValue).then(function (response) {

	          	    	if (response.data)
	          	    	{
	          	    		resolve();
	          	    	}
	          	    	else
	          	    	{
	          	    		reject();	
	          	    	}
	            	}, function () {
             	 		resolve();
	            	});
		        });
		    };
		}
	};
}])
.directive('emailValidation', ['$q','AuthenticationService', function($q,AuthenticationService){
	return {
		require: 'ngModel',
		restrict : 'A',
		link: function($scope, iElm, iAttrs, controller) {
			controller.$asyncValidators.emailValidation = function (modelValue, viewValue) {
		        return $q(function (resolve, reject) {
	          	    AuthenticationService.checkEmail(viewValue).then(function (response) {

	          	    	if (response.data)
	          	    	{
	          	    		resolve();
	          	    	}
	          	    	else
	          	    	{
	          	    		reject();	
	          	    	}
	            	}, function () {
             	 		resolve();
	            	});
		        });
		    };
		}
	};
}])
.directive('pwCheck', [function () {
    return {
      require: 'ngModel',
      link: function (scope, elem, attrs, ctrl) {
        var firstPassword = '#' + attrs.pwCheck;
        elem.bind('keyup', function () {
          scope.$apply(function () {
            //var v = elem.val()===$(firstPassword).val();
            var pass1 = elem.val();
            var pass2 = $(firstPassword).val();
            var result = (pass1==pass2);
            ctrl.$setValidity('pwmatch', result);
          });
        });
      }
    }
  }]);