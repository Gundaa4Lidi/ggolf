var app = angular.module('GolfApp',['ngFileUpload','tm.pagination','ui.select2','ng-sweet-alert',
	'ngImgCrop','ngMaterial','ngMessages','720kb.tooltips','froala','ui.bootstrap','frapontillo.bootstrap-switch']);
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
app.directive('activityView',activityView);
app.directive('pageLoader',['$window', '$document', '$compile',pageLoader]);
app.directive('ngTime',ngTime);

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
app.controller("ClubOrderController",ClubOrderController);
app.controller("CoachApplyController",CoachApplyController);
app.controller("CourseApplyController",CourseApplyController);
app.controller("CoachController",CoachController);
app.controller("MyDatePickerController",MyDatePickerController);

app.controller('PageController', function($scope,$rootScope,$window,$q,$http,$timeout,$interval,appConfig){

	$rootScope.appConfig = appConfig;

	$rootScope.myDatePicker = "page/datePicker/myDatePicker.html";

	$rootScope.processResult = function(data) {
		console.log(data)
		if (data.status == 'Success' || data.status == '200') {
            swal({
                title: "操作成功",
                type: "success",
            })
		} else if(data.status == 'Error'){
			swal({
				title : "操作失败",
				type : "error",
			})
		} else {
			swal({
				title : data.status,
				type : "error"
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

    // function fade(element) {
    //     var op = 1;  // initial opacity
    //     var timer = setInterval(function () {
    //         if (op <= 0.1){
    //             clearInterval(timer);
    //             element.style.display = 'none';
    //         }
    //         element.style.opacity = op;
    //         element.style.filter = 'alpha(opacity=' + op * 100 + ")";
    //         op -= op * 0.1;
    //     }, 10);
    // }
 
	
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
			$scope.currentPage = "page/order/clubOrder.html";
		}
		if(e==8){
			$scope.currentPage = "page/files/files.html";
		}
		if(e==9){
			$scope.currentPage = "page/data/data.html";
		}
		if(e==10.1){
			$scope.currentPage = "page/apply/coachApply.html";
		}
        if(e==10.2){
            $scope.currentPage = "page/apply/courseApply.html";
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


    /**
	 * 计算当前时间
     * @param DateTime
     * @returns {string}
     * @constructor
     */
	$rootScope.CurrentDT = function(DateTime){
    	var t = new Date();
    	var d = new Date(DateTime);
        var dMonth = ((d.getMonth()+1)>9?(d.getMonth()+1).toString():'0' + (d.getMonth()+1));
        var dDay = (d.getDate()>9?d.getDate().toString():'0' + d.getDate());
        var dHour = (d.getHours()>9?d.getHours().toString():'0' + d.getHours());
        var dMin = (d.getMinutes()>9?d.getMinutes().toString():'0'+d.getMinutes());
        var dSec = (d.getSeconds()>9?d.getSeconds().toString():'0'+d.getSeconds());
        var result = "";
        var disDay = Math.abs((t - d))/(1000*60*60*24);

        if(disDay >= 365){
            if(DateTime.lastIndexOf('.')!=-1){
                DateTime = DateTime.substring(0,DateTime.lastIndexOf('.'));
            }
            result = DateTime;
		}
        if(disDay >= 30 && disDay < 365){
        	result = dMonth + "-" + dDay + " " + dHour + ":" + dMin + ":" + dSec;
		}else if(disDay < 30 && disDay >=1){
        	result = disDay.toFixed(0) + "天前" + " " + dHour + ":" + dMin + ":" + dSec;
		}else{
			var disHours = Math.abs((t - d))/(1000*60*60);
			if(disHours < 24 && disHours >= 1){
				result = disHours.toFixed(0) + "小时前";
			}else{
				var disMin = Math.abs((t - d))/(1000*60);
				if(disMin < 60 && disMin >= 1){
                    result = disMin.toFixed(0) + "分钟前";
                }else{
					var disMin = Math.abs((t - d))/1000;
					if(disMin <= 30){
						result = "刚刚";
					}else {
						result = disMin.toFixed(0) + "秒前";
					}
				}
			}
		}
    	return result;
	}

	/*根据出生日期算出年龄*/
    $rootScope.GetAge = function (strDay) {
        var returnAge = -1;
        // if(strBirthday.indexOf("-")==-1){
        	// return returnAge;
		// }
        var bir = new Date(strDay);
        var birthYear = bir.getFullYear();
        var birthMonth = bir.getMonth() + 1;
        var birthDay = bir.getDate();

        var d = new Date();
        var nowYear = d.getFullYear();
        var nowMonth = d.getMonth() + 1;
        var nowDay = d.getDate();

        if (nowYear == birthYear) {
            returnAge = 0;//同年 则为0岁
        }else {
            var ageDiff = nowYear - birthYear; //年之差
            if (ageDiff > 0) {
                if (nowMonth == birthMonth) {
                    var dayDiff = nowDay - birthDay;//日之差
                    if (dayDiff < 0) {
                        returnAge = ageDiff - 1;
                    }else {
                        returnAge = ageDiff;
                    }
                }else {
                    var monthDiff = nowMonth - birthMonth;//月之差
                    if (monthDiff < 0) {
                        returnAge = ageDiff - 1;
                    }else {
                        returnAge = ageDiff;
                    }
                }
            }
        }

        return returnAge;//返回周岁年龄

    }

    $rootScope.formatDT = function(date,format){
        var d = new Date(date);
        var DateTime = null;
        var year = d.getFullYear();
        var month = ((d.getMonth()+1)>9?(d.getMonth()+1).toString():'0' + (d.getMonth()+1));
        var day = (d.getDate()>9?d.getDate().toString():'0' + d.getDate());
        var hour = (d.getHours()>9?d.getHours().toString():'0' + d.getHours());
        var min = (d.getMinutes()>9?d.getMinutes().toString():'0'+d.getMinutes());
        var sec = (d.getSeconds()>9?d.getSeconds().toString():'0'+d.getSeconds());
        switch(format){
            case "yyyy-MM-dd":
                DateTime = year + "-" + month + "-" + day;
                break;
            case "yyyy-MM-dd HH:mm":
                DateTime = year + "-" + month + "-" + day + " " + hour + ":" + min;
                break;
            case "yyyy-MM-dd HH:mm:ss":
                DateTime = year + "-" + month + "-" + day + " " + hour + ":" + min + ":" + sec;
                break;
            default:
                break;
        }
        return DateTime;
    }

    $rootScope.LoadMore = function (Rows,TotalData) {
    	var loadMore = false;
        if(Rows < TotalData){
            loadMore = true;
        }else if(Rows >= TotalData){
            loadMore = false;
        }
        return loadMore;
    }

});