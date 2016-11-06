var loginAuth = angular.module('LoginPage',[]);
loginAuth.controller("LoginBody", ['$scope', '$http', '$window', function($scope, $http, $window) {
	$scope.checkUsers = function() {
		var loginInput = {login: $scope.login};
		$http.post('/login/check', loginInput).then(
			function () {
				$window.location.href = '/welcome';
			}, function () {
				if (response.status == 412) $scope.loginError = "Введеный логин не найден.";
			})
	}
}]);
