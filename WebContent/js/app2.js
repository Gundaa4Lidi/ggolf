var app = angular.module("GGolfApp",['ui.route']);
app.controller("HomeController",['$scope','$interval','$window',$http,function($scope,$interval,$windwo,$http){
	$scope.title = "Home page";
}])

app.config(function($stateProvider,$urlRouterProvider){
	$urlRouterProvider.otherwise('/home');
	$stateProvider
		.state('app',{

		})
		.state('app.home',{
			url:'/home',
			templateUrl:'page/index.html'

		})
		.state('app.shop',{
			url:'/shop',
			templateUrl:'page/shop.html'
		})
		.state('app.allClubList',{
			url:'/allClubList',
			templateUrl:'page/allclublist.html'
		})
})