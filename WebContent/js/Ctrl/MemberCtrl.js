var MemberController = function($scope,$window,$http,$timeout,$interval){
	var sc = $scope;
	
	sc.isLogin = ($window.sessionStorage.token !=null && $window.sessionStorage.token!='null') ? true : false;
	
	sc.user = $window.sessionStorage.name!=null?$window.sessionStorage.name:null;
	
	sc.phone = $window.sessionStorage.userId!=null?$window.sessionStorage.userId:null;
	
	sc.head = $window.sessionStorage.head!=null?$window.sessionStorage.head:null;
	
	sc.load = function(){
		if(!sc.isLogin){
			document.location = 'index.shtml';
	   	}
	}
	
	sc.imgSave = function(head){
		var obj = new Object();
		obj.Head_portrait = head;
		obj.Phone = sc.phone;
		obj = JSON.stringify(obj);
		$http.post('/GGolfz/rest/User/saveHead/'+ obj).then(function(response){
			console.log(response)
			
		})
	}
	
	sc.userImage = "";
	sc.userCroppedImage = "";
	
	var handleFileSelect = function (evt) {
        var file = evt.currentTarget.files[0];
        var reader = new FileReader();
        reader.onload = function (evt) {
        	sc.$apply(function ($scope) {
        		sc.userImage = evt.target.result;
        		//console.log("类型："+typeof sc.userImage,"值:"+sc.userImage)
            });
        };
        reader.readAsDataURL(file);
    };
    angular.element(document.querySelector('#fileInput')).on('change', handleFileSelect);
}

var app = angular.module('app',['ngFileUpload','ngImgCrop']);
app.controller("MemberController",MemberController);
