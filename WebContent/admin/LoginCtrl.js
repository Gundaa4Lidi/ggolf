var app = angular.module('LoginApp',['ng-sweet-alert']);

app.controller("LoginController",function($scope, $http, $window, $timeout){
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
})
