var CoachApplyController = function ($scope,appConfig) {
    var sc = $scope;
    sc.currentCoach = {};
    sc.coachApplyList = [];
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

    sc.$on('loadCoachApply',function () {
        sc.load();
    })

    sc.load = function () {
        sc.getCoachApplyList();
    }

    sc.loading = function(){
        if(sc.loadMore){
            sc.rows += 30;
            sc.getCoachApplyList();
        }
    }

    // sc.$watch('Rows',function(newValue){
    //     if(newValue < sc.TotalMessage){
    //         sc.loadMore = true;
    //     }else if(newValue >= sc.TotalMessage){
    //         sc.loadMore = false;
    //     }
    // })

    sc.checkCoach = function (e) {
        sc.currentCoach = angular.copy(e);
        $("#coach_detail").modal('show');
    }

    /**
     * 获取教练验证列表
     */
    sc.getCoachApplyList = function(){
        var url = appConfig.url + 'Coach/getCoach';
        var method = 'GET';
        var params = {
            days : sc.filterDay,
            keyword : sc.keyword,
            rows : sc.rows
        }
        var promise = sc.httpParams(url,method,params);
        promise.then(function (data) {
            sc.TotalMessage = data.count;
            sc.coachApplyList = data.coachs;
            sc.Rows = sc.rows;
            sc.loadMore = sc.LoadMore(sc.Rows,sc.TotalMessage);
        }),function (data) {
            sc.Load_Failed(data);
        }

    }

    sc.isVerify = function (e) {
        sc.currentCoach = angular.copy(e);
        swal({
            title : "是否批准 "+e.UserName+" 用户\n成为教练?",
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
                sc.VerifyCoach(1);
            }else{
                swal({
                    title : "确认拒绝 "+e.UserName+" 用户的\n教练申请?",
                    text : "确认请按是,否则取消一切操作",
                    type : "warning",
                    showCancelButton : true,
                    confirmButtonColor : "#DD6B55",
                    confirmButtonText : "是",
                    cancelButtonText : "取消",
                    closeOnConfirm : true
                },function (isConfirm) {
                    if(isConfirm){
                        sc.VerifyCoach(2);
                    }
                })
            }
        })

    }


    /**
     * 教练验证
     * @param verify
     * @constructor
     */
    sc.VerifyCoach = function(verify){
        if(verify=='1'||verify=='2'){
            var url = appConfig.url + 'Coach/verifyCoach';
            var method = 'POST';
            var params = {
                Verify : verify,
                CoachID : sc.currentCoach.CoachID,
            }
            var promise = sc.httpParams(url,method,params);
            promise.then(function (data) {
                sc.processResult(data);
                sc.load();
            }),function (data) {
                sc.Load_Failed(data);
            }

        }
    }



    // sc.getCoachDetail = function (CoachID) {
    //     var url = appConfig.url + 'Coach/getCoachDetail';
    //     var method = 'GET';
    //     var params = {
    //         CoachID : CoachID
    //     }
    //     var promise = sc.httpParams(url,method,params);
    //     promise.then(function (data) {
    //
    //     }),function (data) {
    //         sc.Load_Failed(data);
    //     }
    // }

}