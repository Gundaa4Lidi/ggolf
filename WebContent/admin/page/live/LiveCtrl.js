var LiveController = function ($scope,appConfig,$timeout) {
    var sc = $scope;
    sc.LiveList = null;
    sc.TotalLive = 0;
    sc.currentLive = {};
    sc.currentUsers = null;
    sc.currentEvents = [];
//    sc.LiveList = [
//        {
//            CreatorPhoto:'http://192.168.1.107:8085/GGolfz/rest/file/download/user_UH_UH@22.png?t=20170405_151523',
//            CreatorName:'提百万',
//            RoomID:'lv4872354658752',
//            RoomName:'提百万直播',
//            CourseName:'如何一打五'
//        }
//    ]

    sc.load = function () {
        sc.getLiveList();
    }

    sc.filterDays = [
        {key:"默认",   id:"0",value:0},
        {key:"最近7天", id:"1",value:7},
        {key:"最近30天",id:"2",value:30},
        {key:"最近60天",id:"3",value:60},
        {key:"最近90天",id:"4",value:90},
        {key:"最近180天",id:"5",value:180}
    ]
    sc.filterDay = sc.filterDays[0].value;

    sc.getLiveList = function () {
        var url = appConfig.url + "Coach/getCourseVideo";
        var method = 'GET';
        var params = {
            days : sc.filterDay
        };
        var promise = sc.httpParams(url,method,params);
        promise.then(function (data) {
        	console.log(data)
            if(data.status!='Error'){
                sc.LiveList = data.data;
                sc.TotalLive = data.count;
            }else{
                sc.TotalLive = 0;
            }
        })
    }

    sc.pageNum = 0;
    sc.row = 5;
    sc.uPageNum = 0;
    sc.uRow = 30;

    sc.eventPageConfig = {
        currentPage: 1,
        itemsPerPage: 5,
        onChange:function () {
            sc.pageNum = (sc.eventPageConfig.currentPage - 1) * sc.eventPageConfig.itemsPerPage;
            sc.row = sc.eventPageConfig.itemsPerPage;
        }
    }

    sc.userPageConfig = {
        currentPage: 1,
        itemsPerPage: 30,
        onChange:function () {
            sc.uPageNum = (sc.userPageConfig.currentPage - 1) * sc.userPageConfig.itemsPerPage;
            sc.uRow = sc.userPageConfig.itemsPerPage;
        }
    }

    sc.checkLive = function (e) {
        sc.currentLive = angular.copy(e);
        sc.eventPageConfig.totalItems = sc.currentLive.LiveEvent.length;
        $("#LiveDetail").modal("show");
    }


    sc.checkUsers = function (e) {
        sc.currentUsers = angular.copy(e);
        sc.userPageConfig.totalItems = sc.currentUsers.length;
        $("#check_users").modal("show");

    }

}