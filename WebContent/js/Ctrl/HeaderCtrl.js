
var HeaderController = function($http,$scope,$rootScope,$window,$state,$timeout){
	$scope.errorMsg = null;
	
	$scope.infoMsg = null;
	
	$scope.successMsg = null;
	
	$scope.currentNav = 1;
	
	$scope.user = {};
	
	$scope.isLogin = ($window.sessionStorage.token !=null && $window.sessionStorage.token!='null') ? true : false;
	
	$scope.user.name = $window.sessionStorage.name!=null?$window.sessionStorage.name:null;
	
	$scope.user.account = $window.sessionStorage.account!=null?$window.sessionStorage.account:null;
	
	$scope.user.head = $window.sessionStorage.head!=null?$window.sessionStorage.head:null;
	
	$scope.user.userId = $window.sessionStorage.userId!=null?$window.sessionStorage.userId:null;
	
	$rootScope.$on("to-head",function(event,data){
		if(data.head_portrait != undefined){
			$window.sessionStorage.head = data.head_portrait;
			$scope.user.head = data.head_portrait;
		}
		if(data.name != undefined){
			$window.sessionStorage.name = data.name;
			$scope.user.name = data.name;
		}
		/*if(data.phone != undefined){
			$window.sessionStorage.account = data.phone;
			$scope.user.account = data.phone;
		}*/
	})
	
	$scope.login = function(){
		$http.post("rest/User/logon?phone="+$scope.login.phone+"&password="+$scope.login.pwd).then(function(response) {
			console.log(response);
			if(response.status == 200){
				if(response.data.errorMsg != null){
					swal(response.data.errorMsg,"","error");
				}else{
					console.log(response.data)
					$window.sessionStorage.token = response.data.token;
					$window.sessionStorage.account = response.data.account;
					$window.sessionStorage.name = response.data.name;
					$window.sessionStorage.head = response.data.head;
					$window.sessionStorage.userId = response.data.userId;
					$scope.isLogin = true;
					$scope.user.name = $window.sessionStorage.name;
					$scope.user.account = $window.sessionStorage.account;
					$scope.user.head = $window.sessionStorage.head;
					$scope.user.userId = $window.sessionStorage.userId;
					$state.go('app.member',{args:$scope.user.userId});
				}
			}else{
				swal("账户或密码错误","","error");
			}
			
		});
	}
	
	$scope.toPage = function(id,args){
		console.log("id:"+id,"args:"+args);
		if(id==1){
			$state.go('app.home');
		}
		if(id==2){
			$state.go('app.member',{args:args});
		}
		if(id==3){
			$state.go('app.clubBooking');
		}
		if(id==4){
			$state.go('app.clubDetail');
		}
		if(id==5){
			$state.go('app.clubList');
		}
		if(id==6){
			$state.go('app.clubOrder');
		}
		if(id==7){
			$state.go('app.clubSearch');
		}
		if(id==8){
			$state.go('app.shoppingOrderInfo');
		}
		if(id==9){
			$state.go('app.contactUs');
		}
		if(id==10){
			$state.go('app.aboutUs');
		}
		$scope.currentNav = id;
	}
	
	$scope.logOff = function(){
		logoff();
		$state.go('app.home');
		$scope.load();
	}
	
	function logoff(){
		$window.sessionStorage.token = null;
		$window.sessionStorage.userId = null;
		$window.sessionStorage.name = null;
		$window.sessionStorage.head = null;
		$scope.isLogin=false;
	}
	
	$scope.inputcheck = false;
    $scope.wait = 60;
    $scope.wait2 = 180;
    $scope.selectNav = null;
    $scope.load = function(){
    	
    }
   
    $scope.editPwd = function(){
    	$('#update-password').modal('show');
    }

    $scope.checkPhone2 = function(phone){
        if (phone && /^1[3|4|5|7|8]\d{9}$/.test(phone)) {
            $('#updateCode').attr("disabled","true");
            $http.post("rest/User/sendcode2?phone="+phone).then(function(response) {
                alert(response);
                if(response!='Error'){
                    CodeTime2();
                }
            });
        } else {
            //不对
            $scope.errorMsg = '请输入正确的手机号码 ';
            alert($scope.errorMsg)
             $scope.register.phonenum = '';
            $('#updateCode').removeAttr("disabled");
        }
    	resetMsg();
    }
    
    $scope.checkPhone = function(phone){
        if (phone && /^1[3|4|5|7|8]\d{9}$/.test(phone)) {
            $('#getCode').attr("disabled","true");
            $http.post("rest/User/sendcode?phone="+phone).then(function(response) {
                alert(response.data);
                if(response.data !='Error'){
                    CodeTime();
                }
            });
        } else {
            //不对
            $scope.errorMsg = '请输入正确的手机号码 ';
            alert($scope.errorMsg);
             $scope.register.phonenum = '';
            $('#getCode').removeAttr("disabled");
        }
    	resetMsg();

    }
    
    function ErrorMsg(data){
    	swal(data,"","warring");
    }
    
    function SuccessMsg(data){
    	swal(data,"","success");
    }

    $scope.register = function(){
		if($scope.register.Name == '' ||$scope.register.Name == undefined){
			$scope.errorMsg = "昵称不能为空";
			alert($scope.errorMsg)
		}else if($scope.register.phonenum == '' ||$scope.register.phonenum == undefined){
		    $scope.errorMsg = "手机号码不能为空";
		    alert($scope.errorMsg)
        }else if($scope.register.pwd == '' ||$scope.register.pwd == undefined){
		    $scope.errorMsg = "密码不能为空";
		    alert($scope.errorMsg)
        }else if($scope.register.pwd2 == '' ||$scope.register.pwd2 == undefined){
            $scope.errorMsg = "确认密码不能为空";
            alert($scope.errorMsg)
        }else if($scope.register.code == '' ||$scope.register.code == undefined){
            $scope.errorMsg = "验证码不能为空";
            alert($scope.errorMsg)
        }else if($scope.register.pwd==$scope.register.pwd2){
            $('#getCode').attr('disabled',"true");
            $http.post('rest/User/create?phone='
            		+$scope.register.phonenum+
            		'&password='
            		+$scope.register.pwd+
            		'&code='
            		+$scope.register.code+
            		'&Name='
            		+$scope.register.Name).then(function(response){
            			console.log(response)
		                if(response.data.status=='Success'){
		                    $scope.successMsg = response.status;
		                    $scope.infoMsg = "注册成功，将跳转到个人主页";
		                    $scope.login.phone = $scope.register.phonenum;
		                    $scope.login.pwd = $scope.register.pwd;
		                    swal($scope.infoMsg,"","success")
		                    $timeout(function(){
		                    	$scope.login();
		                    },1000);
		                }else{
		                    $scope.errorMsg = response.data.errorMsg;
		                    alert($scope.errorMsg)
		                }
            		});	
            
        }else{
            $scope.errorMsg="两次密码不一致";
            alert($scope.errorMsg)
        }
		resetMsg();
    }
    
    function resetMsg(){
    	$timeout(function(){
			$scope.errorMsg = null;
			
			$scope.infoMsg = null;
			
			$scope.successMsg = null;
		},3000)
    }

    function CodeTime(){
    	if ($scope.wait2 == 0) {
    		$('#getCode').removeAttr('disabled');
    		$('#getCode').val("获取验证码");
    		$scope.wait2 = 180;
    	} else{
    		$('#getCode').attr("disabled","true");
    		$('#getCode').val($scope.wait2 + "秒后重置");
    		$scope.wait2--;
    		setTimeout(function(){
    			CodeTime();
    		},1000)
    	}
    }
    
    function CodeTime2(){
    	if ($scope.wait2 == 0) {
    		$('#updateCode').removeAttr('disabled');
    		$('#updateCode').val("获取验证码");
    		$scope.wait2 = 180;
    	} else{
    		$('#updateCode').attr("disabled","true");
    		$('#updateCode').val($scope.wait2 + "秒后重置");
    		$scope.wait2--;
    		setTimeout(function(){
    			CodeTime2();
    		},1000)
    	}
    }
    $scope.updatePwd = function(){
        if($scope.update.phonenum == '' ||$scope.update.phonenum == undefined){
            $scope.errorMsg = "手机号码不能为空";
            alert($scope.errorMsg)
        }else if($scope.update.pwd == '' ||$scope.update.pwd == undefined){
            $scope.errorMsg = "密码不能为空";
            alert($scope.errorMsg)
        }else if($scope.update.pwd2 == '' ||$scope.update.pwd2 == undefined){
            $scope.errorMsg = "确认密码不能为空";
            alert($scope.errorMsg)
        }else if($scope.update.code == '' ||$scope.update.code == undefined){
            $scope.errorMsg = "验证码不能为空";
            alert($scope.errorMsg)
        }else if($scope.update.pwd==$scope.update.pwd2){
            $('#updateCode').attr('disabled',"true");
            $http({
                method: 'POST',
                url: 'rest/User/updatePassword',
                headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                transformRequest: function(obj) {
                    var str = [];
                    for(var p in obj)
                        str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                    return str.join("&");
                },
                data: {
                    phone:$scope.update.phonenum,
                    password:$scope.update.pwd,
                    code:$scope.update.code,
                }
            }).success(function(response) {
                console.log(response)
                if(response.status=='Success'){
                    $scope.successMsg = response.status;
                    $scope.infoMsg = "修改成功，将3秒内跳转到个人主页";
                    logoff();
                    $scope.login.phone = $scope.update.phonenum;
                    $scope.login.pwd = $scope.update.pwd;
                    $scope.login();
                    $timeout(function(){
                    	$state.go('app.member');
                    })
                    alert($scope.infoMsg)	
                }else{
                    $scope.errorMsg = response.errorMsg;
                    alert($scope.errorMsg)
                }
            });
        }else{
            $scope.errorMsg="两次密码不一致";
            alert($scope.errorMsg)
        }
    }
    
    $scope.processResult = function(data) {
		console.log(data)
		if (data.status == 'Success') {
			swal({
				title : "操作成功",
				type : "success",
			})
		} else {
			swal({
				title : "操作失败",
				type : "error",
			})
		}
	}

    function time() {
        if ($scope.wait == 0) {

            $('#getCode').removeAttr("disabled");

            $('#getCode').val("获取验证码");//改变按钮中value的值

            $scope.wait = 60;

        } else {

            $('#getCode').attr("disabled","true");//倒计时过程中禁止点击按钮

            $('#getCode').val($scope.wait + "秒后重置");//改变按钮中value的值

            $scope.wait--;

            setTimeout(function() {
                time();//循环调用
            },1000)

        }
    }
}