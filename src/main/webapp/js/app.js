/**
 * back-user anglanjs 相关注册
 * */

'use strict';

var app = angular.module("app", ['ui.bootstrap', 'tm.pagination', 'ui.router'], function ($httpProvider) {
    $httpProvider.defaults.headers.post['Content-Type'] = 'application/json';
    $httpProvider.defaults.headers.post['X-Requested-With'] = 'XMLHttpRequest';
})
    .run(//
        ['$rootScope', '$state', '$stateParams',
            function ($rootScope, $state, $stateParams) {
                $rootScope.$state = $state;
                $rootScope.$stateParams = $stateParams;
            }
        ]
    )
    .config(function ($stateProvider, $urlRouterProvider) {
        $urlRouterProvider.otherwise('/access/signin');

        $stateProvider
            .state('app', {
                abstract: true,
                url: '/app',
                templateUrl: 'partials/template/app.html',
                controller: 'AppController'
            })
            .state('app.configuration', {
                url: '/configuration',
                templateUrl: 'partials/configuration.html',
                controller: 'ConfigurationController'
            })
            .state('access', {
                url: '/access',
                template: '<div ui-view class="fade-in-right-big smooth"></div>'
            })
            .state('access.signin', {
                url: '/signin',
                templateUrl: 'partials/signin.html',
                controller: 'SigninController'
            })
            .state('access.signup', {
                url: '/signup',
                templateUrl: 'partials/signup.html',
                controller: 'SignupController'
            })
    });