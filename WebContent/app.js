var app = angular.module('GolfApp',['ui.router','ngFileUpload','ngImgCrop','ui.bootstrap','ng-sweet-alert','ngSanitize']);
app.config(['$stateProvider','$urlRouterProvider',function($stateProvider,$urlRouterProvider){
	$urlRouterProvider.otherwise('/app/home');
	$stateProvider
		.state('app',{
			url: '/app',
			abstract:true,
			views:{
				'header':{
					templateUrl: 'inc/headerbox.html',
					controllerAs:'HomeController as HC'
				},
				'content':{
					abstract:true,
					template:'<div ui-view="content.detail"></div>'
				},
				'footer':{
					templateUrl: 'inc/footer.html',
				}
			}
		})
		.state('app.home',{
			url:'/home',
			views:{
				'content.detail':{
					templateUrl: 'page/home/home.html',
				}
			}

		})
		.state('app.member',{
			url:'/member/{args}',
			views:{
				'content.detail':{
					templateUrl: 'page/member/member.html',
				}
			}
		})
		.state('app.clubBooking',{
			url:'/clubBooking/{args}',
			views:{
				'content.detail':{
					templateUrl: 'page/club/clubBooking.html',
				}
			}
		})
		.state('app.clubDetail',{
			url:'/clubDetail/{args}',
			views:{
				'content.detail':{
					templateUrl: 'page/club/clubDetail.html',
				}
			}
		})
        .state('app.clubList',{
            url:'/clubList/{args}',
            views:{
                'content.detail':{
                    templateUrl: 'page/club/clubList.html',
                }
            }
        })
        .state('app.clubOrder',{
            url:'/clubOrder/{args}',
            views:{
                'content.detail':{
                    templateUrl: 'page/club/clubOrder.html',
                }
            }
        })
        .state('app.clubSearch',{
            url:'/clubSearch/{args}',
            views:{
                'content.detail':{
                    templateUrl: 'page/club/clubSearch.html',
                }
            }
        })
        .state('app.shoppingOrderInfo',{
            url:'/shoppingOrderInfo/{args}',
            views:{
                'content.detail':{
                    templateUrl: 'page/shop/shoppingOrderInfo.html',
                }
            }
        })
        .state('app.contactUs',{
            url:'/contactUs/{args}',
            views:{
                'content.detail':{
                    templateUrl: 'page/about/contactUs.html',
                }
            }
        })
        .state('app.aboutUs',{
            url:'/aboutUs/{args}',
            views:{
                'content.detail':{
                    templateUrl: 'page/about/aboutUs.html',
                }
            }
        })

}]);
/*app.config(function($httpProvider) {
    var token;
    if (window.sessionStorage.token&&window.sessionStorage.token.length>0) {
        token=window.sessionStorage.token
    }
    console.log(token)
    $httpProvider.defaults.headers.common['auth'] = token;
})*/


app.controller("AppCtrl", AppCtrl);
app.controller("HeaderController",HeaderController);
app.controller("HomeController",HomeController);
app.controller("MemberController",MemberController);
app.controller("AboutUsController",AboutUsController);
app.controller("ContactUsController",ContactUsController);
app.controller("ClubBookingController",ClubBookingController);
app.controller("ClubDetailController",ClubDetailController);
app.controller("ClubListController",ClubListController);
app.controller("ClubOrderController",ClubOrderController);
app.controller("ClubSearchController",ClubSearchController);
app.controller("ShoppingOrderInfoController",ShoppingOrderInfoController);



