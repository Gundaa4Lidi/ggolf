var ClubDetailController = function($scope,appConfig){
    var sc = $scope;
    var ClubPageCtrl = sc.$parent;
    var CreateTime = new Date();
    sc.myInterval = 'none';
    sc.noWrapSlides = false;
    sc.pause = true;
    sc.active = 0;
    sc.format = "yyyy-MM-dd";
    sc.currentClub = new Object();
    sc.clubServe = new Object();
    sc.clubServes = new Object();
    sc.status = {
        dateOpen : false,
    }
    sc.weekdays = ["一","二","三","四","五","六","日","其他"];
    sc.initTime = '2017-04-14 06:00';
    var d = new Date(sc.initTime);
    sc.dateTimes = [];
    var addTime = function () {
        for(var i= 0; i < 29; i++){
            d.setMinutes(d.getMinutes()+30)
            var hour = (d.getHours()>9?d.getHours().toString():'0' + d.getHours());
            var minute = (d.getMinutes()>9?d.getMinutes().toString():'0'+d.getMinutes());
            var time = hour + ":" + minute;
            sc.dateTimes.push(time);
        }
    }
    


    sc.back = function(){
        ClubPageCtrl.changeClubPage(1);
    }

    sc.IsOpen = function () {
        sc.status.dateOpen = true;
    }

    sc.load = function(){
        addTime();
        sc.address(ClubPageCtrl.clubData);
        sc.getClubDetail();
        sc.getClubServe();
    }

    sc.$watch("currentClub.CreateTime",function (data) {
        var d = new Date(data);
        var month = ((d.getMonth()+1)>9?(d.getMonth()+1).toString():'0' + (d.getMonth()+1));
        var day = (d.getDate()>9?d.getDate().toString():'0' + d.getDate());
        var hour = (d.getHours()>9?d.getHours().toString():'0' + d.getHours());
        CreateTime = d.getFullYear() + "-" +month + "-" +day;
    })

    $scope.dateOptions = {
        formatDay: 'dd',
        formatMonth: 'MM',
        formatYear: 'yyyy',
        formatDayHeader: 'EEE',
        formatDayTitle: 'MMMM yyyy',
        formatMonthTitle: 'yyyy',
        datepickerMode: 'day',
        minMode: 'day',
        maxMode: 'year',
        showWeeks: true,
        startingDay: 0,
        yearRange: 20,
        minDate: null,
        maxDate: null,
        shortcutPropagation: false
    };

    /**
     * 获取球场明细
     */
    sc.getClubDetail = function() {
        var url = appConfig.url + "Club/getClubDetail";
        var method = 'GET';
        var params = {
            ClubID : ClubPageCtrl.clubData.ClubID
        }
        var promise = sc.httpParams(url,method,params);
        promise.then(function (data) {
            if(data.status == 'Error'){
                var club = angular.copy(ClubPageCtrl.clubData);
                sc.currentClub.ClubID = club.ClubID;
                sc.currentClub.PhotoList = club.ClubPhoto;
                sc.currentClub.ClubName = club.ClubName;
                sc.currentClub.TotalHole = club.TotalHole;
                sc.currentClub.TotalStemNum = club.TotalStemNum;
                sc.currentClub.PhoneNum = club.ClubPhoneNumber;
                sc.saveClubDetail().then(function (data) {
                    sc.load();
                }),function (data) {
                    sc.Load_Failed(data);
                }
            }else{
                data.CreateTime = new Date(data.CreateTime);
                sc.currentClub = data;
            }
        })
    }

    /**
     * 提交表单
     */
    sc.submitDetailForm = function(isValid){
        if(isValid){
            var promise = sc.saveClubDetail();
            promise.then(function (data) {
                sc.processResult(data);
                if(data.status == 'Success'){
                    sc.saveClub();
                    sc.load();
                }
            }),function (data) {
                sc.Load_Failed(data);
            }
        }
    }

    /**
     * 保存球场明细
     */
    sc.saveClubDetail = function(){
        sc.currentClub.CreateTime = CreateTime;
        var url = appConfig.url + 'Club/saveClubDetail';
        var method = 'POST';
        var data = sc.currentClub;
        return sc.httpDataUrl(url,method,data);
    }

    /**
     * 保存球场
     */
    sc.saveClub = function () {
        var club = angular.copy(ClubPageCtrl.clubData);
        if(sc.p){
            club.Province = sc.p;
        }
        if(sc.c){
            club.City = sc.c;
        }
        if(sc.a){
            club.Area = sc.a;
        }
        if(sc.d){
            club.ClubAddress = sc.d;
        }
        if(!sc.p || !sc.c || !sc.d){
            swal("请确认地址是否填写正确!","","warning");
        }else{
            club.ClubName = sc.currentClub.ClubName;
            club.TotalHole = sc.currentClub.TotalHole;
            club.TotalStemNum = sc.currentClub.TotalStemNum;
            club.ClubPhoneNumber = sc.currentClub.PhoneNum;
            var url = appConfig.url + 'Club/saveClub';
            var method = 'POST';
            var data = club;
            var promise = sc.httpDataUrl(url,method,data);
            promise.then(function (data) {
                // sc.processResult(data);
            }),function(data){
                sc.Load_Failed(data);
            }
        }
    }

    sc.pageConfig = {
        currentPage: 1,
        maxSize : 5,
    }

    /**
     * 获取供应商
     */
    sc.getClubServe = function () {
        var club = angular.copy(ClubPageCtrl.clubData);
        var url = appConfig.url + "Club/getClubServeByClubID";
        var method = 'GET';
        var params = {
            keyword : sc.serveKeyword,
            ClubID : club.ClubID,
            rows : sc.pageConfig.maxSize,
            pageNum : sc.pageConfig.currentPage
        }
        var promise = sc.httpParams(url,method,params);
        promise.then(function (data) {
            sc.clubServes = data.clubServes;
            sc.totalItems = data.count;
        }),function (data) {
            sc.Load_Failed(data);
        }
    }
    
    sc.addServe = function () {
        sc.clubServe = new Object();
        $("#addClubServe").modal("show");
    }

    sc.editServe = function(e) {
        sc.clubServe = e;
        $("#addClubServe").modal("show");
    }

    sc.submitServeForm = function (Isvalid) {
        if(Isvalid){
            sc.saveClubServe();
        }
    }

    sc.saveClubServe = function () {
        sc.clubServe.ClubID = sc.currentClub.ClubID;
        sc.clubServe.ClubName = sc.currentClub.ClubName;
        var url = appConfig.url + 'Club/saveClubServe';
        var method = 'POST';
        var data = sc.clubServe;
        var promise = sc.httpDataUrl(url,method,data);
        promise.then(function (data) {
            sc.processResult(data);
            $("#addClubServe").modal("hide");
            sc.getClubServe();
        }),function (data) {
            sc.Load_Failed(data);
        }
    }



    sc.oneAtATime = true;

    sc.status = {
        open : false,
        isCustomHeaderOpen: false,
        isFirstOpen: true,
        isFirstDisabled: false
    };
}