var ClubDetailController = function($scope,appConfig,$timeout){
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
    sc.oneAtATime = true;
    sc.status = {
        dateOpen : false,
        dateTimeOpen : false,
    }
    sc.weekdays = ["一","二","三","四","五","六","日"];
    sc.Payment = [
        {key:"全额支付",value:0},
        {key:"球场现付",value:1}
    ];
    sc.dateTimes = [];
    sc.morningTimes = [];
    sc.afternoonTimes = [];
    sc.nightTimes = [];


    $timeout(function () {
        var initTime = '2017-04-14 06:00';
        var d = new Date(initTime);
        console.log(d)
        for(var i= 0; i < 29; i++){
            d.setMinutes(d.getMinutes()+30)
            var hour = (d.getHours()>9?d.getHours().toString():'0' + d.getHours());
            var minute = (d.getMinutes()>9?d.getMinutes().toString():'0'+d.getMinutes());
            var time = hour + ":" + minute;
            var mm = d.getHours() * 60 + d.getMinutes() ;
            if(mm <= 12 * 60){
                sc.morningTimes.push(time);
            }else if(mm > 12 * 60 && mm <= (19 * 60)){
                sc.afternoonTimes.push(time);
            }else{
                sc.nightTimes.push(time);
            }
            sc.dateTimes.push(time);

        }
    },200);

    sc.today = function(){
        sc.DateTime = new Date();
    }
    sc.today();

    sc.back = function(){
        ClubPageCtrl.changeClubPage(1);
    }

    sc.IsOpen = function (i) {
        if(i==1){
            sc.status.dateOpen = true;
        }
        if(i==2){
            sc.status.dateTimeOpen = true;
        }
    }

    sc.load = function(){
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
    });
    // sc.$watch('DateTime',function (newValue,oldValue) {
    //     console.log("newValue:"+newValue+",oldValue:"+oldValue);
    // })

    sc.checkDT = function () {
        console.log(sc.DateTime);
    }

    sc.dateOptions = {
        minMode: 'day',
        maxMode: 'year',
        showWeeks: true,
        startingDay: 0,
        yearRange: 20,
        minDate: null,
        maxDate: null,
        shortcutPropagation: false
    };
    sc.options = {
        minDate: sc.DateTime,
        showWeeks: true
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
                    sc.edit = false;
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
            if(data.status == 'Success'){
                sc.processResult(data);
                $("#addClubServe").modal("hide");
                sc.getClubServe();
            }else if(data.ClubserveID){
                $("#addClubServe").modal("hide");
                sc.saveTimes(data);
                sc.getClubServe();
            }
        }),function (data) {
            sc.Load_Failed(data);
        }
    }

    /**
     * 删除供应商
     * @param e
     */
    sc.removeClubServe = function(e){
        swal({
            title: "删除供应商 " +e.Name+ "?" ,
            text: "",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "是的",
            cancelButtonText: "取消",
            closeOnConfirm: false
        },function () {
            var url = appConfig.url + 'Club/removeClubServe';
            var method = 'POST';
            var params = {
                ClubserveID : e.ClubserveID,
            }
            var promise = sc.httpParams(url,method,params);
            promise.then(function (data) {
                sc.processResult(data);
                if(data.status == 'Success'){
                    sc.load();
                }
            }),function (data) {
                sc.Load_Failed(data);
            }
        });

    }

    /**
     * 查看时间段的信息
     * @param time
     * @param week
     * @param clubserve
     */
    sc.checkTime = function (time,week,clubserve,$index,e) {
        sc.manIndex(e,$index);
        var url = appConfig.url + "Club/getPricesByClubserveID";
        var method = 'GET';
        var params = {
            Week : week,
            Time : time,
            ClubserveID : clubserve.ClubserveID
        }
        var promise = sc.httpParams(url,method,params);
        promise.then(function (data) {
            sc.currentTime = new Object();
            sc.currentTime = data;
            sc.currentTime.Type = parseInt(data.Type);
        }),function (data) {
            sc.Load_Failed(data);
        }
    }

    /**
     * 初始化各个时段的信息
     * @param e
     * @param week
     * @param time
     * @constructor
     */
    sc.saveTimes = function(e){
        var timeList = [];
        for(var i in sc.weekdays){
            if(sc.weekdays[i] != '其他'){
                for(var j in sc.dateTimes){
                    var data = {
                        ClubserveID : e.ClubserveID,
                        ClubID : e.ClubID,
                        Week : sc.weekdays[i],
                        Time : sc.dateTimes[j],
                        DownPayment : '500',
                        OtherPrice : '0',
                        Type : '0',
                        IsPrivilege : 0,
                        IsDeposit : '0',
                    }
                    timeList.push(data);
                }

            }
        }
        if(timeList.length > 0){
            var url = appConfig.url + "Club/createPriceForClubServe";
            var method = 'POST';
            var data = timeList;
            var promise = sc.httpDataUrl(url,method,data);
            promise.then(function (data) {
                sc.processResult(data);
            }),function (data) {
                sc.Load_Failed(data);
            }
        }

    }

    sc.priceSubmit = function ($valid) {
        if($valid){
            var url = appConfig.url + "Club/updatePriceForClubServe";
            var method = 'POST';
            var data = sc.currentTime;
            var promise = sc.httpDataUrl(url,method,data);
            promise.then(function (data) {
                sc.processResult(data);
            }),function (data) {
                sc.Load_Failed(data);
            }
        }

    }

    sc.serveIndex = 0;
    sc.weekIndex = 0;
    sc.mTimeIndex = null;
    sc.aTimeIndex = null;
    sc.nTimeIndex = null;

    sc.cnIndex = function (index) {
        if(index!=sc.serveIndex){
            sc.currentTime = new Object();
            sc.serveIndex = index;
        }
    }
    sc.wnIndex = function (index) {
        if(index != sc.weekIndex){
            sc.currentTime = new Object();
            sc.weekIndex = index;
            sc.manIndex(null);
        }

    }

    /**
     * 标识当前时间段
     * @param e
     * @param index
     */
    sc.manIndex = function(e,index){
        if(e == 'm'){
            sc.mTimeIndex = index;
            sc.aTimeIndex = null;
            sc.nTimeIndex = null;
        }else if(e == 'a'){
            sc.mTimeIndex = null;
            sc.aTimeIndex = index;
            sc.nTimeIndex = null;
        }else if(e == 'n'){
            sc.mTimeIndex = null;
            sc.aTimeIndex = null;
            sc.nTimeIndex = index;
        }else{
            sc.mTimeIndex = null;
            sc.aTimeIndex = null;
            sc.nTimeIndex = null;
        }

    }


}