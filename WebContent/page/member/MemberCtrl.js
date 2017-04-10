var MemberController= function($scope,$state,$stateParams,$rootScope,$timeout,$interval,$window,$http){
	var sc = $scope;
	sc.currentUser = null;
	sc.currentUD = {};
	sc.editPhone = false;
	sc.wait2 = 180;
	sc.isLogin = ($window.sessionStorage.token !=null && $window.sessionStorage.token!='null') ? true : false;
	sc.edit = function(){
		sc.editPhone = false;
	}
	
	sc.load = function(){
		$timeout(function() {
	        fade(document.getElementById('page-loader'));
	    }, 200);
		if(!sc.isLogin){
			$state.go('app.home');
	   	}else{
	   		sc.getUser();
	   	}
		
	}
	sc.getUser = function(){
		var userID = $stateParams.args;
		$http.get('/GGolfz/rest/User/getUserByUserID?UserID='+userID).then(function(response){
			console.log(response.data);
			sc.currentUser = response.data
			sc.name = sc.currentUser.name!=null?sc.currentUser.name:null;
			sc.phone = sc.currentUser.phone!=null?sc.currentUser.phone:null;
			sc.head = sc.currentUser.head_portrait!=null?sc.currentUser.head_portrait:null;
			sc.$emit("to-head",sc.currentUser);
		});
			/*swal("账号错误,请重新登录","","error");*/
	}
	
	sc.getUserDetail = function(){
		if(sc.currentUser.userID != null){
			$http.get('/GGolfz/rest/UserDetail/getUserDetail?UserID='+sc.currentUser.userID).then(function(response){
				console.log(response.data)
				if(response.data.status == "Error"){
					console.log(sc.name)
					sc.currentUD.UserName = sc.name;
					sc.currentUD.PhoneNum = sc.phone;
				}else{
					sc.currentUD = response.data;
				}
				console.log(sc.currentUD)
				
			})
		}else{
			swal("用户编号出错");
		}
	}
	
	sc.saveUserDetail = function(){
		var user = sc.currentUser;
		sc.currentUD.HeadPhoto = user.head_portrait;
		sc.currentUD.UserID = user.userID;
		var data = sc.currentUD;
		console.log(data)
		if(data.PhoneNum != null && /^1[3|4|5|7|8]\d{9}$/.test(data.PhoneNum)){
			data = JSON.stringify(data);
			$http.post('/GGolfz/rest/UserDetail/saveUserDetail',data).then(function(response){
				sc.load();
				sc.processResult(response.data)
			})
		}else{
			swal("请输入正确的手机号码","","warning");
		}
	}
	
	function fade(element) {
        var op = 1;  // initial opacity
        var timer = $interval(function () {
            if (op <= 0.1){
                $interval.cancel(timer);
                element.style.display = 'none';
            }
            element.style.opacity = op;
            element.style.filter = 'alpha(opacity=' + op * 100 + ")";
            op -= op * 0.1;
        }, 10);
    }
	
	sc.imgSave = function(head){
		var obj = sc.currentUser;
		obj.head_portrait = head;
		obj.phone = sc.phone;
		obj = JSON.stringify(obj);
		$http.post('/GGolfz/rest/User/saveHead/', obj).then(function(response){
			sc.load();
			sc.processResult(response)
			
		})
	}
	
	// sc.processResult = function(data) {
	// 	console.log(data)
	// 	if (data.status == 'Success'|| data.status == 200) {
	// 		swal({
	// 			title : "操作成功",
	// 			type : "success",
	// 		})
	// 	} else {
	// 		swal({
	// 			title : "操作失败",
	// 			type : "error",
	// 		})
	// 	}
	// }
	
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
    
    sc.bindingPhone = function(){
    	$http.post('/GGolfz/rest/User/bindingPhone?phone='
    			+sc.currentUD.PhoneNum+
    			'&code='+sc.code+
    			'&userID='+sc.currentUser.userID).then(function(response){
    		console.log(response.data);
    		if(response.data.errorMsg!=null){
    			swal(response.data.errorMsg,"","error");
    		}
    	})
    }
    
    sc.checkPhone = function(phone){
    	var msg = '';
        if (phone && /^1[3|4|5|7|8]\d{9}$/.test(phone)) {
            $('#getCode').attr("disabled","true");
            $http.post("rest/User/sendcode?phone="+phone).then(function(response) {
                if(response.data == "该号码已注册,请直接登陆"){
                	msg = "该号码已存在,请更换号码操作";
                	swal(msg);
                }else if(response.data!='Error'){
                	msg = response.data
                	swal(msg)
                    CodeTime();
                }
            });
        } else {
            //不对
            var errorMsg = '请输入正确的手机号码 ';
            swal(errorMsg,"","warning");
            $('#getCode').removeAttr("disabled");
        }

    }
    
    function CodeTime(){
    	if (sc.wait2 == 0) {
    		$('#getCode').removeAttr('disabled');
    		$('#getCode').val("获取验证码");
    		sc.wait2 = 180;
    	} else{
    		$('#getCode').attr("disabled","true");
    		$('#getCode').val(sc.wait2 + "秒后重置");
    		sc.wait2--;
    		setTimeout(function(){
    			CodeTime();
    		},1000)
    	}
    }
}