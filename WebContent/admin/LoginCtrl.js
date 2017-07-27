var app = angular.module('LoginApp',['ng-sweet-alert']);

app.controller("LoginController",function($scope,$rootScope,$q, $http, $window, $timeout){
    var AppIP = "http://192.168.1.107:8085";
    $scope.login = function(){
        $http.post("/GGolfz/rest/Staff/logon?StaffID="+$scope.staffid+"&password="+$scope.password).then(function(response) {
            console.log(response)
            var data = response.data;
            console.log(data)
            if(data.token!=null){
                $window.sessionStorage.token = data.token;
                $window.sessionStorage.StaffId = data.StaffId;
                $window.sessionStorage.Staffname = data.Staffname;
                $window.sessionStorage.Position = data.Position;
                $window.sessionStorage.StaffHead = data.StaffHead;
                $window.sessionStorage.OnlineCount = data.OnlineCount;
                $window.sessionStorage.OnlineStaff = JSON.stringify(data.OnlineStaff);
                swal("登录成功","稍后跳转到主界面","success");
                $timeout(function(){
                	document.location = 'index.html';
                },1000)
            }else{
            	console.log(data)
               /*  alert(response); */
               swal(data,"","error");
            }
        });
    }
    // $scope.login = function () {
    //     console.log(AppIP);
    //     var url = "/GGolfz/rest/Staff/logon";
    //     var method = 'POST';
    //     var params = {
    //         StaffID : $scope.staffid,
    //         password : $scope.password
    //     }
    //     var promise = $scope.httpParams(url,method,params);
    //     promise.then(function (data) {
    //         console.log(data)
    //         if(data.token!=null){
    //             $window.sessionStorage.token = data.token;
    //             $window.sessionStorage.StaffId = data.StaffId;
    //             $window.sessionStorage.Staffname = data.Staffname;
    //             $window.sessionStorage.Position = data.Position;
    //             $window.sessionStorage.StaffHead = data.StaffHead;
    //             $window.sessionStorage.OnlineCount = data.OnlineCount;
    //             $window.sessionStorage.OnlineStaff = JSON.stringify(data.OnlineStaff);
    //             swal("登录成功","稍后跳转到主界面","success");
    //             $timeout(function(){
    //                 document.location = 'index.html';
    //             },1000)
    //         }else{
    //             console.log(data)
    //             /*  alert(response); */
    //             swal(data,"","error");
    //         }
    //     }),function (data) {
    //         $scope.Load_Failed(data);
    //     }
    // }
    //
    // $rootScope.httpDataUrl = function(url,method,data){
    //     var dfd = $q.defer();
    //     $http({
    //         url: url,
    //         method: method,
    //         data: data,
    //     }).then(function(response){
    //         dfd.resolve(response.data);
    //     },function(response){
    //         $rootScope.errorResult(response)
    //         dfd.reject(response.data);
    //     })
    //     return dfd.promise;
    // }
    // $rootScope.httpParams = function(url,method,params){
    //     var dfd = $q.defer();
    //     $http({
    //         url: url,
    //         method: method,
    //         params:params,
    //     }).then(function(response){
    //         dfd.resolve(response.data);
    //     },function(response){
    //         $rootScope.errorResult(response)
    //         dfd.reject(response.data);
    //     })
    //     return dfd.promise;
    // }
    //
    // $rootScope.errorResult = function(response){
    //     // console.log(response)
    //     swal("网络异常\n"+response.status,response.statusText,"warning");
    // }
    //
    // $rootScope.Load_Failed = function(data){
    //     swal("加载失败",data,"warning");
    // }
})
