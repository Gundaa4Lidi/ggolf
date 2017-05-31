var UserController = function($scope,$rootScope,$http,$window,$q,$timeout,Upload){
	var sc = $scope;
	sc.Users = null;
	sc.currentItem = null;
	sc.currentItemD = null;
	sc.clubList = null;
	sc.applyCoach = new Object();
    sc.format = "yyyy-MM-dd";
    sc.today = new Date();
	// this.myDate = new Date();
  	// this.isOpen = false;
    sc.status = {
        IsDateOpen : false,
        IsBtdOpen : false,
        IsErrorDate : false,
        IsErrorDate1: false
    }

    sc.TotalUser = 0;
    sc.rows = 20;
    sc.Rows = 0;
    sc.loadMore = false;

    sc.loading = function(){
        sc.rows += 20;
        sc.getUserList();
    }
    // sc.$watch('Rows',function(e){
    //     if(e < sc.TotalUser){
    //         sc.loadMore = true;
    //     }else if(e >= sc.TotalUser){
    //         sc.loadMore = false;
    //     }
    // })

    sc.load = function(){
		sc.getUserList();
    }

    sc.Lon = null;
    sc.Lat = null;

	sc.getUserList = function () {
        var url = appConfig.url + 'User/getUserInfo2';
        var method = 'GET';
        var params = {
            Rows : sc.rows,
            Keyword : sc.keyword,
			Lon : sc.Lon,
			Lat : sc.Lat
        };
        var promise = sc.httpParams(url,method,params);
        promise.then(function(data){
            sc.Users = data.userList;
            sc.TotalUser = data.count;
            sc.Rows = sc.rows;
        }),function(data){
            sc.Load_Failed(data);
        }
        sc.loadMore = sc.LoadMore(sc.Rows,sc.TotalUser);
    }

	// sc.selectOptions = [
     //   {title:"全部",id:"0"},
	//    {title:"球场",id:"1"},
	//    {title:"商品",id:"2"},
	//    {title:"动态",id:"3"},
	//    {title:"用户",id:"4"},
	//    {title:"头条",id:"5"},
	//    {title:"教练",id:"6"}
	// ];
	// sc.statusOpt = {
	// 	allowClear:true
	// }
	// sc.sortord = sc.selectOptions[0].title;
    sc.bir = null;
	sc.sen = null;
	sc.Age = null;
	sc.Sen = null;
    /**
     * 监听生日
     */
    sc.$watch("Birthday",function (data) {
        if(data){
            sc.bir = sc.getDate(data);
            sc.Age = sc.GetAge(sc.bir);
        }
    })

    /**
     * 监听入行日期
     */
    sc.$watch("Seniority",function (data) {
        if(data){
            sc.sen = sc.getDate(data);
            sc.Sen = sc.GetAge(sc.sen);
        }
    })

    sc.getDate = function (data) {
        var d = new Date(data);
        var month = ((d.getMonth()+1)>9?(d.getMonth()+1).toString():'0' + (d.getMonth()+1));
        var day = (d.getDate()>9?d.getDate().toString():'0' + d.getDate());
        // var hour = (d.getHours()>9?d.getHours().toString():'0' + d.getHours());
        var result = d.getFullYear() + "-" +month + "-" +day;
        return result;
    }

    sc.birthdayOptions = {
        minMode: 'day',
        maxMode: 'year',
        showWeeks: true,
        startingDay: 0,
        yearRange: 20,
        minDate: null,
        maxDate: sc.today,
        shortcutPropagation: false
    };

    sc.updateDate = function () {
        if(sc.Birthday > sc.today){
            sc.status.IsErrorDate = true;
        }else{
            sc.status.IsErrorDate = false;
            sc.minYear = new Date().getFullYear() - (sc.GetAge(sc.getDate(sc.Birthday))-18);
            console.log(sc.minYear)
            sc.minDate = new Date(sc.minYear,0,1);
            // sc.maxYear = new Date().getFullYear() - parseInt(sc.Birthday)
            // sc.maxDate = new Date(sc.maxYear,11,31);
            sc.dateOptions = {
                minMode: 'day',
                maxMode: 'year',
                showWeeks: true,
                startingDay: 0,
                yearRange: 20,
                minDate: sc.minDate,
                maxDate: sc.today,
                shortcutPropagation: false
            };
            if(sc.Seniority){
                if(sc.Seniority < sc.Birthday){ //判断入行日期是否小于出生日期
                    sc.status.IsErrorDate1 = true;
                }else {
                    sc.status.IsErrorDate1 = false;
                }
            }
        }
    }



    // sc.changBirthday = function () {
    //     sc.birth = new Date(sc.Birthday);
    //     if(sc.birth > sc.today){
    //         sc.status.IsErrorDate = true;
    //     }else{
    //         sc.status.IsErrorDate = false;
    //     }
    // }


    
    // sc.pageInfo = {
    // 	rows:3,
    // 	onChange: function(){
    //     	$scope.search();
    //     }
    // }
    
    sc.search = function(){
    	sc.getUserList();
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
				sc.currentItemD = obj;
				dfd.resolve(obj);
			}else{
				sc.currentItemD = data;
				dfd.resolve(data);
			}
		},function(response){
			sc.errorResult(response);
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


    sc.IsOpen = function (i) {
        if(i==1){
            sc.status.IsBtdOpen = true;
        }
        if(i==2){
            sc.status.IsDateOpen = true;
        }
    }


	sc.ApplyCoach = function (e) {
		sc.applyCoach = new Object();
		sc.Club = new Object();
		sc.Age = new Object();
		sc.Birthday = new Object();
		sc.applyCoach.CoachID = e.userID;
		sc.applyCoach.UserName = e.name;
		sc.applyCoach.CoachHead = e.head_portrait;
        sc.applyCoach.CoachPhone = e.phone;
		sc.getClubList();
    }

	sc.submitCoachApply = function ($valid) {
		if($valid){
            sc.applyCoach.ClubName = sc.Club.ClubName;
            sc.applyCoach.ClubID = sc.Club.ClubID;
            sc.applyCoach.Birthday = sc.bir;
            sc.applyCoach.Seniority = sc.sen;
            var url = appConfig.url + 'Coach/saveCoach';
            var method = 'POST';
            var data = sc.applyCoach;
            var promise = sc.httpDataUrl(url,method,data);
            promise.then(function (data) {
				sc.processResult(data);
				if(data.status=='Success'){
                    $('#apply-open-coach').modal("hide");
                    sc.load();
                }

            }),function (data) {
				sc.Load_Failed(data);
            }
		}
    }

    sc.getClubList = function(){
        var url = appConfig.url + 'Club/getClubByKeyword';
        var method = 'GET';
        var params = {
            clubType : '练习场'
        };
        var promise = sc.httpParams(url,method,params);
        promise.then(function(data){
            sc.clubList = data.data;
        }),function(data){
            sc.Load_Failed(data);
        }
    }



}