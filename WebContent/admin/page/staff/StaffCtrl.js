var StaffController = function($http, $scope, $rootScope, $window, $timeout,Upload) {
	var sc = $scope;
	sc.Staffs = null;
	sc.TotalStaff = 0;
	sc.currentStaff = new Object();

	sc.load = function() {
		$http.get('/GGolfz/rest/Staff/GetAll').then(function(response) {
			console.log(response.data.length)
			sc.Staffs = response.data;
			sc.TotalStaff = sc.Staffs.length;
		})
	}

	sc.Works =[
		{workPlace:"艾泽拉斯",id:"1"},
		{workPlace:"爱琴海",id:"2"}
	];
	
	$scope.$on('update_head',function(event,data){
		console.log(data)
		if(data){
			sc.load();
		}
	})
	
	sc.save = function() {
		var data = JSON.stringify(sc.currentStaff);
		if (sc.currentStaff.StaffName == null
				|| sc.currentStaff.StaffName == '') {
			alert('员工名必须');
		} else if (sc.currentStaff.StaffID == null
				|| sc.currentStaff.StaffID == '') {
			alert('登录名必须');
		} else if (sc.currentStaff.Password == null
				|| sc.currentStaff.Password == '') {
			alert('密码必须');
		} else if (sc.currentStaff.Password != sc.currentStaff.Password2) {
			alert('密码不一致');
		} else {
			$http.post('/GGolfz/rest/Staff', data).then(function(response) {
				sc.processResult(response.data);
				sc.load();
			});
		}
	}

	sc.addStaff = function(){
		
		sc.currentStaff = new Object();
		sc.currentStaff.Head = "../img/user_empty.png";
		console.log("新增员工",sc.currentStaff)
	}
	
	sc.edit = function(e) {
		sc.currentStaff = e;
		sc.currentStaff.Password2 = sc.currentStaff.Password;
	}
	
	sc.remove = function(e) {
		$http.post('/GGolfz/rest/Staff/delete/' + e.StaffID, {}).then(function(response){
			sc.processResult(response.data);
			sc.load();
		})
	}
	
	sc.deleteStaff = function(e){
		swal({
			title : "删除   "+e.StaffName+" 这名员工?",
			text : "",
			type : "warning",
			showCancelButton : true,
			confirmButtonColor : "#DD6B55",
			confirmButtonText : "是的",
			cancelButtonText : "取消",
			closeOnConfirm : false
		}, function() {
			sc.remove(e);
		});
	}
	
	sc.upload = function (files) {
        if (files && files.length) {
            for (var i = 0; i < files.length; i++) {
                var file = files[i];
                Upload.upload({
                    url: '/GGolfz/rest/file/uploadPhoto',
                    file: file
                }).progress(function (evt) {
                    var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
                    console.log('progress: ' + progressPercentage + '% ' + evt.config.file.name);
                }).success(function (data, status, headers, config) {
                	sc.currentStaff.Head = data+'?t='+new Date();
                    console.log('file ' + config.file.name + 'uploaded. Response: ' + data);
                }).error(function (data, status, headers, config) {
                	console.log(data,status,headers,config)
                	alert('上传失败');
                    console.log('error status: ' + status);
                })
            }
        }
    };
    
    sc.staffImage = '';
    sc.staffCroppedImage = '';
    
    sc.imgSave = function(e){
    	sc.currentStaff.Head = e;
    }

    var handleFileSelect = function (evt) {
        var file = evt.currentTarget.files[0];
        var reader = new FileReader();
        reader.onload = function (evt) {
        	sc.$apply(function ($scope) {
        		sc.staffImage = evt.target.result;
        		console.log("类型："+typeof sc.staffImage,"值:"+sc.staffImage)
            });
        };
        reader.readAsDataURL(file);
    };
    angular.element(document.querySelector('#fileInput')).on('change', handleFileSelect);

	// sc.processResult = function(data) {
	// 	console.log(data)
	// 	if (data.status == 'Success') {
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
}