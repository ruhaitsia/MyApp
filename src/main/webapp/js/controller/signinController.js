app.controller('SigninController', ['$scope', '$http', '$state', function($scope, $http, $state){
    $scope.user = {};
    $scope.authError = null;
    $scope.login = function () {
        $scope.authError = null;

        if ("Admin" === $scope.user.name && "Admin" === $scope.user.password) {
            $state.go('app.configuration');
        } else {
            $scope.authError = 'Email or Password not right';
        }
    };

    $scope.keyLogin = function($event) {
        if( 13 == $event.keyCode ){
            $scope.login();
        };
    }

}]);



