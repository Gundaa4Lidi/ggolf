var CourseApplyController = function ($scope,appConfig) {
    var sc = $scope;
    sc.currentCourse = {};
    sc.courseApplyList = [];
    sc.filterDays = [
        {key:"默认",   id:"0",value:0},
        {key:"最近7天", id:"1",value:7},
        {key:"最近30天",id:"2",value:30},
        {key:"最近60天",id:"3",value:60},
        {key:"最近90天",id:"4",value:90},
        {key:"最近180天",id:"5",value:180}
    ]
    sc.filterDay = sc.filterDays[0].value;
    sc.filterDaySelect = {
        width:"122"
    }
    sc.rows = 60;
    sc.Rows = 0;
    sc.loadMore = false;
    sc.TotalMessage = 0;

    /**
     * 默认加载
     */
    sc.load = function () {
        sc.getCourseApplyList();
    }

    /**
     * 加载数据
     */
    sc.loading = function(){
        if(sc.loadMore) {
            sc.rows += 30;
            sc.getCourseApplyList();
        }
    }

    /**
     * 监听数量是否达到上限
     */
    // sc.$watch('Rows',function(newValue){
    //     if(newValue < sc.TotalMessage){
    //         sc.loadMore = true;
    //     }else if(newValue >= sc.TotalMessage){
    //         sc.loadMore = false;
    //     }
    // })

    /**
     * 查看
     * @param e
     */
    sc.checkCourse = function (e) {
        sc.currentCourse = angular.copy(e);
        $("#course_detail").modal("show");
    }

    /**
     * 获取列表
     */
    sc.getCourseApplyList = function () {
        var url = appConfig.url + 'Coach/getCourse';
        var method = 'GET';
        var params = {
            days : sc.filterDay,
            keyword : sc.keyword,
            rows : sc.rows
        }
        var promise = sc.httpParams(url,method,params);
        promise.then(function (data) {
            sc.TotalMessage = data.count;
            sc.courseApplyList = data.courses;
            sc.Rows = sc.rows;
        }),function (data) {
            sc.Load_Failed(data);
        }
        sc.loadMore = sc.LoadMore(sc.Rows,sc.TotalMessage);
    }

    /**
     * 确认课程是否通过验证
     * @param verify
     * @param CourseID
     */
    sc.verifyCourse = function(verify,CourseID){
        var url = appConfig.url + 'Coach/CourseVerify';
        var method = 'POST';
        var params = {
            Verify : verify,
            CourseID : CourseID
        }
        var promise = sc.httpParams(url,method,params);
        promise.then(function (data) {
            sc.processResult(data)
            sc.load();
        }),function (data) {
            sc.Load_Failed(data);
        }
    }

    sc.isVerify = function (e) {
        swal({
            title : "是否批准 "+e.CoachName+" 教练\n申请课程\n "+e.Title+"?",
            text : "通过请按是,拒绝请按否",
            type : "warning",
            showCancelButton : true,
            confirmButtonColor : "#DD6B55",
            confirmButtonText : "是",
            cancelButtonText : "否",
            closeOnConfirm : false,
            closeOnCancel : false
        },function (isConfirm) {
            if(isConfirm){
                sc.verifyCourse(1,e.CourseID);
            }else{
                swal({
                    title : "确认拒绝 "+e.CoachName+" 教练的\n课程申请?",
                    text : "确认请按是,否则取消一切操作",
                    type : "warning",
                    showCancelButton : true,
                    confirmButtonColor : "#DD6B55",
                    confirmButtonText : "是",
                    cancelButtonText : "取消",
                    closeOnConfirm : true
                },function (isConfirm) {
                    if(isConfirm){
                        sc.verifyCourse(2,e.CourseID);
                    }
                })
            }
        })

    }

}