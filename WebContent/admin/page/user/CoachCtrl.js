var CoachController = function($scope,appConfig){
	var sc = $scope;
	sc.Coachs = null;
	sc.currentCoach = null;
	sc.CoachCourse = null;
	sc.currentCourse = null;
    sc.TotalCoach = 0;
    sc.totalCourse = 0;
    sc.rows = 20;
    sc.Rows = 0;
    sc.loadMore = false;
    sc.isvideo = false;

    sc.loading = function(){
        sc.rows += 20;
        sc.getCoachList();
    }
    // sc.$watch('Rows',function(e){
    //     if(e < sc.TotalCoach){
    //         sc.loadMore = true;
    //     }else if(e >= sc.TotalCoach){
    //         sc.loadMore = false;
    //     }
    // })

	sc.load = function () {
        sc.getCoachList();
    }

    sc.getCoachList = function () {
		var url = appConfig.url + 'Coach/getCoach';
		var method = 'GET';
		var params = {
            keyword : sc.keyword,
            IsVerify : '1',
            rows : sc.rows
		}
        var promise = sc.httpParams(url,method,params);
        promise.then(function(data){
            sc.Coachs = data.coachs;
            sc.TotalCoach = data.count;
            sc.Rows = sc.rows;
        }),function(data){
            sc.Load_Failed(data);
        }
        sc.loadMore = sc.LoadMore(sc.Rows,sc.TotalCoach);
    }

    sc.checkCoachDetail = function (e) {
		sc.getCoachDetail(e.CoachID);
		$("#coach_detail").modal("show");
    }

    sc.checkCoachCourse = function (e) {
        sc.currentCoach = angular.copy(e);
        sc.getCourseByCoachID(e.CoachID);
        $("#view_course").modal("show");
    }

    sc.rate = 4;
    sc.max = 5;
    sc.isReadonly = true;

    sc.hoveringOver = function(value) {
        sc.overStar = value;
        sc.percent = 100 * (value / sc.max);
    };

    sc.getCoachDetail = function (CoachID) {
        var url = appConfig.url + 'Coach/getCoachDetail';
        var method = 'GET';
        var params = {
            CoachID : CoachID
        }
        var promise = sc.httpParams(url,method,params);
        promise.then(function(data){
            sc.currentCoach = data;
            sc.rate = parseInt(data.coachScore.Score).toFixed(0);
        }),function(data){
            sc.Load_Failed(data);
        }
    }

    sc.pageConfig = {
        currentPage: '1',
        maxSize :'5',
    }

    /**
     * 获取当前教练的课程
     * @param CoachID
     */
    sc.getCourseByCoachID = function (CoachID) {
        var url = appConfig.url + 'Coach/getCourseByCoachID';
        var method = 'GET';
        var params = {
            CoachID : CoachID,
            // keyword : sc.courseKey,
            rows : sc.pageConfig.maxSize,
            pageNum : sc.pageConfig.currentPage,
        }
        var promise = sc.httpParams(url,method,params);
        promise.then(function(data){
            console.log(data);
            sc.CoachCourse = data.courses;
            sc.totalCourse = data.count;
        }),function(data){
            sc.Load_Failed(data);
        }
    }

    sc.selectTeachMethod = [
        {key:"预约教学",value:"0"},
        {key:"视频直播教学",value:"1"},
        {key:"套课教学",value:"2"}
    ]

    sc.Method = sc.selectTeachMethod[0].value;

    /**
     * 查看
     * @param e
     */
    sc.checkCourse = function (e) {
        sc.currentCourse = angular.copy(e);
        if(e.IsBatch == '1'){
            sc.Method = sc.selectTeachMethod[2].value;
        }else if(e.IsVideo == '1'){
            sc.Method = sc.selectTeachMethod[1].value;
        }else{
            sc.Method = sc.selectTeachMethod[0].value;
        }
        // $("#view_course").modal("hide");
        $("#course_detail").modal("show");
    }

    sc.ApplyCourse = function () {
        var coach = sc.currentCoach;
        sc.currentCourse = new Object();
        sc.currentCourse.CoachHead = coach.CoachHead;
        sc.currentCourse.CoachPhone = coach.CoachPhone;
        sc.currentCourse.CoachName = coach.CoachName;
        $("#view_course").modal("hide");
        $("#apply-open-course").modal("show");
    }

    sc.cleanMethod =function () {
        if(sc.Method!='2'){
            sc.currentCourse.ClassHour = null;
            sc.currentCourse.Valid = null;
        }
    }

    sc.submitCourseApply = function ($valid) {
        if($valid){
            sc.currentCourse.CoachID = sc.currentCoach.CoachID;
            if(sc.Method=='1'){//视频教学
                sc.currentCourse.ClassHour = "1";
                sc.currentCourse.IsVideo = '1';
                if(sc.currentCourse.MaxPeople>4){
                    swal("视频教学的人数上限不能超过4人","","warning");
                    return;
                }
            }else if(sc.Method=='2'){//套课教学
                sc.currentCourse.IsBatch = '1';
            }else{
                sc.currentCourse.ClassHour = "1";
                sc.currentCourse.IsVideo = '0';
                sc.currentCourse.IsBatch = '0';
            }
            console.log(sc.currentCourse.MaxPeople);
            var url = appConfig.url + 'Coach/ApplyCourse';
            var method = 'POST';
            var data = sc.currentCourse;
            var promise = sc.httpDataUrl(url,method,data);
            promise.then(function(data){
                sc.processResult(data);
                if(data.status!='Error'){
                    $("#apply-open-course").modal("hide");
                    $("#view_course").modal("show");
                    sc.getCourseByCoachID(sc.currentCoach.CoachID);
                }
            }),function(data){
                sc.Load_Failed(data);
            }
        }
    }



}