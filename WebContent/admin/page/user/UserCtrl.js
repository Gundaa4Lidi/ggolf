var UserController = function($scope,$rootScope,$http,$window,$q,$timeout,Upload){
	var sc = $scope;
	sc.processResult = $rootScope.processResult;
	sc.errorResult = $rootScope.errorResult;
	sc.Users = null;
	sc.currentItem = null;
	sc.currentItemD = null;
	sc.applyCoach = new Object();
	sc.TotalUser = 0;
	this.myDate = new Date();
	  this.isOpen = false;
	sc.selectOptions = [
       {title:"全部",id:"0"},                 
	   {title:"球场",id:"1"},
	   {title:"商品",id:"2"},
	   {title:"动态",id:"3"},
	   {title:"用户",id:"4"},
	   {title:"头条",id:"5"},
	   {title:"教练",id:"6"}
	];
	sc.statusOpt = {
		allowClear:true
	}
	sc.sortord = sc.selectOptions[0].title;
	sc.applyCoach.Age = new Date();
    
    sc.load = function(){
    	$http.get('/GGolfz/rest/User/getAll').then(function(response){
    		console.log(response.data)
    		sc.Users = response.data;
    		sc.TotalUser = sc.Users.length;
    	})
		
	}
    
    sc.pageInfo = {
    	rows:3,
    	onChange: function(){
        	$scope.search();
        }
    }
    
    sc.search = function(){
    	console.log(sc.searchWord)
    	var params = {
			rows:sc.pageInfo.rows,
			keyword:sc.searchWord,
			type:sc.sortord
		}
    	$http({
    		url:'/GGolfz/rest/Search/getSearch',
    		method: 'GET',
    		params: params
    	}).then(function(response){
    		
    		console.log(response)
    	},function(response){
    		sc.errorReuslt(response);
    	})
    }
	
	sc.editUser = function(e){
		sc.currentItem = e;
	}
	
	sc.checkUser = function(e){
		sc.currentItemD = new Object();
		var dfd = $q.defer();
		$http({
			url:'/GGolfz/rest/UserDetail/getUserDetail?&UserID='+e.userID,
			method: 'GET'
		}).then(function(response){
			var data = response.data;
			if(data.status != null){
				var obj = new Object();
				obj.HeadPhoto = e.head_portrait;
				obj.Sex = e.sex;
				obj.PhoneNum = e.phone;
				obj.name = e.name;
				console.log(obj)
				sc.currentItemD = obj;
				dfd.resolve(obj);
			}else{
				console.log(data)
				sc.currentItemD = data; 
				dfd.resolve(data);
			}
		},function(response){
			sc.errorReuslt(response);
			dfd.reject(response);
		})
		return dfd.promise;
		
	}
	
	
	sc.upload = function (files) {
        if (files && files.length) {
            for (var i = 0; i < files.length; i++) {
                var file = files[i];
                Upload.upload({
                    url: '/GGolfz/rest/file/upload',
                    file: file
                }).progress(function (evt) {
                    var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
                    console.log('progress: ' + progressPercentage + '% ' + evt.config.file.name);
                }).success(function (data, status, headers, config) {
                	sc.currentUser.head_portrait = data;
                    console.log('file ' + config.file.name + 'uploaded. Response: ' + data);
                }).error(function (data, status, headers, config) {
                	console.log(data,status,headers,config)
                	alert('上传失败');
                    console.log('error status: ' + status);
                })
            }
        }
    };
}