var app = angular.module('GolfApp',['ngFileUpload','tm.pagination','ui.select2','ng-sweet-alert',
	'ngImgCrop','ngKeditor','ngMaterial','ngMessages','720kb.tooltips','froala','ui.bootstrap']);
app.value('froalaConfig',{
    toolbarInline: false,
    placeholderText: 'Enter Text Here'
});

app.constant('appConfig',appConfig);
app.constant('cities',cities);
app.config(['tooltipsConfProvider','$httpProvider',function (tooltipsConfProvider,$httpProvider) {
    var token;
    if (window.sessionStorage.token&&window.sessionStorage.token.length>0) {
        token=window.sessionStorage.token
    }
    $httpProvider.defaults.headers.common['auth'] = token;
    tooltipsConfProvider.configure({
        'size': 'large',
        'speed': 'fast'
    });
}]);

app.directive('activeNav',activeNav);
app.directive('lightgallery',lightgallery);
app.directive('errSrc',errSrc);
app.directive('scrollTop',['$window', '$document', '$compile',scrollTop]);
app.directive('selectAddress',['$http', '$q', '$compile','cities',selectAddress]);

app.controller('UserController',UserController);
app.controller('CoachController',CoachController);
app.controller('StaffController',StaffController);
app.controller('ClubPageController',ClubPageController);
app.controller('ClubController',ClubController);
app.controller('ClubDetailController',ClubDetailController);
app.controller('CoursePageController',CoursePageController);
app.controller('CourseController',CourseController);
app.controller('CourseDetailController',CourseDetailController);
app.controller("DataController",DataController);
app.controller("ArticlePageController",ArticlePageController);
app.controller("ArticleController",ArticleController);
app.controller("ArticleDataController",ArticleDataController);
app.controller("CommentController",CommentController);
app.controller("SystemPageController",SystemPageController);
app.controller("SystemMsgController",SystemMsgController);
app.controller("SystemDataController",SystemDataController);
app.controller("UserMsgPageController",UserMsgPageController);
app.controller("UserMsgController",UserMsgController);
app.controller("UserMsgDataController",UserMsgDataController);

app.controller('PageController', function($scope,$rootScope,$window,$q,$http,$timeout,$interval){
	
	$rootScope.processResult = function(data) {
		console.log(data)
		if (data.status == 'Success' || data.status == '200') {
            swal({
                title: "操作成功",
                type: "success",
            })
		} else {
			swal({
				title : "操作失败",
				type : "error",
			})
		}
	}
	$rootScope.errorResult = function(response){
		console.log(response)
		swal("网络异常\n"+response.status,response.statusText,"warning");
	}

	$rootScope.Load_Failed = function(data){
		swal("加载失败",data,"warning");
	}
	
	$scope.load = function(){
		console.log($window.sessionStorage.token)
		var s = 1;
	   	if($window.sessionStorage.token==null||$window.sessionStorage.token=="null"){
	   		$interval(function(){
	   			swal({
	   				title:"用户未登录",
	   				text:s+"秒后跳转登录界面",
	   				type:"warning",
	   				showConfirmButton:false,
	   			})
	   			if(s == 0){
	   				document.location = 'login.html';
	   			}
	   			s--;
	   		},1000)
	   	}else{
	   		$scope.navigate(3);
	   		
	   		$scope.currentStaffHead = $window.sessionStorage.StaffHead;
	   		
	   		$scope.currentStaffID = $window.sessionStorage.StaffId;
	   		
	   		$scope.currentStaffName = $window.sessionStorage.Staffname;
	   		
	   		$scope.Position = $window.sessionStorage.Position;
	   		
	   		
	   	}
	}
	
	
	function fade(element) {
        var op = 1;  // initial opacity
        var timer = setInterval(function () {
            if (op <= 0.1){
                clearInterval(timer);
                element.style.display = 'none';
            }
            element.style.opacity = op;
            element.style.filter = 'alpha(opacity=' + op * 100 + ")";
            op -= op * 0.1;
        }, 10);
    }
 
	
	$scope.navigate = function(e){
		if(e==1.1){
			$scope.currentPage = "page/user/user.html";
		}
		if(e==1.2){
			$scope.currentPage = "page/user/coach.html";
		}
		if(e==2.1){
			$scope.currentPage = "page/message/systemMsgPage.html";
		}
		if(e==2.2){
			$scope.currentPage = "page/message/userMsgPage.html";
		}
		if(e==3){
			$scope.currentPage = "page/staff/staff.html";
		}
		if(e==4){
			$scope.currentPage = "page/club/clubPage.html";
		}
		if(e==5){
			$scope.currentPage = "page/course/coursePage.html";
		}
		if(e==6){
			$scope.currentPage = "page/article/articlePage.html";
		}
		if(e==7){
			$scope.currentPage = "page/order/order.html";
		}
		if(e==8){
			$scope.currentPage = "page/files/files.html";
		}
		if(e==9){
			$scope.currentPage = "page/data/data.html";
		}
		if(e==10){
			$scope.currentPage = "page/apply/apply.html";
		}
		if(e==11){
			$scope.currentPage = "page/comment/comment.html";
		}
		if(e==12){
			$scope.currentPage = "page/live/live.html";
		}
        $scope.page = e;

    }
    
	$scope.changepwd = function(){
		if($scope.currentPassword!=null&&$scope.newPassword!=null&&$scope.confirmPassword!=null){
			if($scope.newPassword==$scope.confirmPassword){				
				$http.post("/GGolfz/rest/Staff/update?StaffID=" +$window.sessionStorage.StaffId+"&password="+$scope.currentPassword+"&newpassword="+$scope.confirmPassword).then(function(response) {
					if(response.data=="密码更新成功"){
						swal(response.data,"2秒后跳转登录界面","success");
						$timeout(function(){
							document.location = 'login.html';
						},2000)
					}else{
						swal(response.data,"","error");
					}
					
				});
			}else{
				swal("新密码两次输入不对等","","warning");
			}
		}else{
			swal("必须填充新旧密码","","warning");
		}			
	 }
	
	$scope.imgSave = function(e){
		var dfd = $q.defer()
		$http({
			url:'/GGolfz/rest/Staff/saveHead',
			method:'POST',
			data:{
				StaffID: $scope.currentStaffID,
				Head: e
			}
		}).then(function(response){
			dfd.resolve(response.data);
			console.log(response)
			$rootScope.processResult(response);
			if(response.status == 200){
				console.log("200");
				var staff = response.data;
                $scope.$broadcast('update_head',true);
                $window.sessionStorage.StaffHead = staff.Head;
                $scope.currentStaffHead = staff.Head;
            }
		},function(response){
			$rootScope.errorResult(response)
			dfd.reject(response)
		})
		return dfd.promise;
	}
	
	$scope.staffImage = null;
	$scope.staffCroppedImage = null;
	
	$scope.handleFileSelect = function (files) {
        if (files && files.length){
        	for(var i = 0; i < files.length; i++){
                var file = files[i];
                var reader = new FileReader();
                reader.onload = function (evt) {
                    $scope.$apply(function ($scope) {
                        $scope.staffImage = evt.target.result;
                    });
                };
                reader.readAsDataURL(file);
            }
        }
    };
    /*angular.element(document.querySelector('#fileInput')).on('change', handleFileSelect);*/
    
    $scope.logOff = function(){
    	$window.sessionStorage.token=null;
    	$window.sessionStorage.StaffHead = null;
    	$window.sessionStorage.StaffId = null;
		$window.sessionStorage.Staffname = null;
		$window.sessionStorage.Position = null;
    	document.location = 'login.html';
    }

    $rootScope.httpDataUrl = function(url,method,data){
        var dfd = $q.defer();
        $http({
            url: url,
            method: method,
            data: data
        }).then(function(response){
            dfd.resolve(response.data);
            console.log(response)
        },function(response){
            $rootScope.errorResult(response)
            dfd.reject(response.data);
        })
        return dfd.promise;
    }

    $rootScope.httpParams = function(url,method,params){
        var dfd = $q.defer();
        $http({
            url: url,
            method: method,
            params:params
        }).then(function(response){
            dfd.resolve(response.data);
            console.log(response)
        },function(response){
            $rootScope.errorResult(response)
            dfd.reject(response.data);
        })
        return dfd.promise;
    }

    // //上传文件
    // $rootScope.uploadService = function (files) {
    // 	var dfd = $q.defer();
    //     if (files && files.length) {
    //         for (var i = 0; i < files.length; i++) {
    //             var file = files[i];
    //             Upload.upload({
    //                 url: appConfig.url + 'file/upload',
    //                 file: file
    //             }).progress(function (evt) {
    //                 var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
    //                 console.log('progress: ' + progressPercentage + '% ' + evt.config.file.name);
    //             }).success(function (data) {
    //                 dfd.resolve(data)
    //             }).error(function (data) {
    //                 dfd.reject(data)
    //             })
    //         }
    //         return dfd.promise;
    //     }
    // };


});