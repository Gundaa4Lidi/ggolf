var ClubDetailController = function($scope,appConfig,$timeout){
    var sc = $scope;
    var ClubPageCtrl = sc.$parent;

    sc.myInterval = 'none';
    sc.noWrapSlides = false;
    sc.pause = true;
    sc.active = 0;
    sc.format = "yyyy-MM-dd";
    sc.currentClub = new Object();
    sc.clubServe = new Object();
    sc.clubServes = new Object();
    sc.fairwayFrom=new Object();
    sc.oneAtATime = true;
    sc.loading = false;
    sc.status = {
        dateOpen : false,
        dateTimeOpen : false,
        uibOpen : false,
        UseDateOpen : false,
    }
    sc.weekdays = ["一","二","三","四","五","六","日","other"];
    sc.Payment = [
        {key:"全额支付",value:0},
        {key:"球场现付",value:1}
    ];
    sc.dateTimes = [];
    sc.morningTimes = [];
    sc.afternoonTimes = [];
    sc.nightTimes = [];
    sc.currentFairway = {};
    sc.fairwayList = [];
    sc.DTime = null;

/////////////////////////////////////////////////////////////////map//////////////////////////////////////////////////////////////////////////////

    sc.createPointLng = null;
    sc.createPointLat = null;
    sc.createPointAddress = null;

    //初始化地图
    var map = new AMap.Map('mapView',{
        zoom: 10,
        center: [113.12,23.02],//new AMap.LngLat(116.39,39.9)
        scrollWheel : false
    });
    map.plugin(['AMap.ToolBar'],function(){
        map.addControl(new AMap.ToolBar());
    });
    var marker = null;

    var Marker = function (lng,lat) {
        marker = new AMap.Marker({
            position: [lng,lat]
        });
        marker.setMap(map);
    }
    Marker(113.12,23.02);

    var createPointInfowindowContent = $('<div>\
                                            <div id="address"></div>\
                                            <div id="lng"></div>\
                                            <div id="lat"></div>\
                                            <div class="p-t-10">\
                                                <button id="readyPoint" class="btn btn-danger">确认标点</button>\
                                            </div>\
                                          </div>');

    var createPointInfowindow = new AMap.InfoWindow({
        content: createPointInfowindowContent[0],
        offset: new AMap.Pixel(0, 0),
        size:new AMap.Size(230,0)
    })

    createPointInfowindowContent.find('#readyPoint').on('click',function () {
        map.clearMap();
        Marker(sc.createPointLng,sc.createPointLat);

    })

    marker.on('click',function (e) {
        console.log(e)
    })

    map.on('click',function (e) {
        sc.createPointLng=e.lnglat.getLng();
        sc.createPointLat=e.lnglat.getLat();
        sc.createPointAddress=null;
        createPointInfowindow.open(map,new AMap.LngLat(sc.createPointLng,sc.createPointLat));
        createPointInfowindowContent.find('#address').text('地址:'+'正在查询,请稍等。。。');
        createPointInfowindowContent.find('#lng').text('经度:'+sc.createPointLng);
        createPointInfowindowContent.find('#lat').text('经度:'+sc.createPointLat);

        AMap.service(["AMap.Geocoder"], function() { //加载地理编码
            geocoder = new AMap.Geocoder({
                radius: 1000,
                extensions: "all"
            });
            // Marker(createPointLng,createPointLat);
            //步骤三：通过服务对应的方法回调服务返回结果，本例中通过逆地理编码方法getAddress回调结果
            geocoder.getAddress(new AMap.LngLat(sc.createPointLng,sc.createPointLat), function(status, result){
                //根据服务请求状态处理返回结果
                if(status=='error') {
                    alert("服务请求出错啦！ ");
                }
                if(status=='no_data') {
                    alert("无数据返回，请换个关键字试试～～");
                }
                else {
                    console.log(result)
                    sc.createPointAddress= result.regeocode.formattedAddress;

                    createPointInfowindowContent.find('#address').text('地址:'+sc.createPointAddress);
                }
            });
        });
    });

//////////////////////////////////////////////////////end-map////////////////////////////////////////////////////////////



    $timeout(function () {
        var initTime = '2017-04-14 06:00';
        var d = new Date(initTime);
        console.log(sc.appConfig)
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

    sc.back = function(){
        ClubPageCtrl.changeClubPage(1);
    }

    sc.IsOpen = function (i) {
        if(i==1){
            sc.status.dateOpen = !sc.status.dateOpen;
        }
        if(i==2){
            sc.status.dateTimeOpen = !sc.status.dateTimeOpen;
        }
        if(i==3){
            sc.status.LTDateOpen = !sc.status.UseDateOpen;
        }
    }

    sc.load = function(){
        sc.address(ClubPageCtrl.clubData);
        sc.getClubDetail();
        sc.getClubServe();
        sc.getFairwayList();

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
        minMode: 'day',
        maxMode: 'year',
        showWeeks: true,
        startingDay: 0,
        yearRange: 20,
        minDate: new Date(),
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
                sc.saveClubDetail().then(function () {
                    sc.load();
                }),function (data) {
                    sc.Load_Failed(data);
                }
            }else{
                sc.CreateTime = new Date(data.CreateTime);
                sc.currentClub = data;
                // console.log(sc.currentClub);
            }
        }),function (data) {
            sc.Load_Failed(data);
        }
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
        sc.currentClub.CreateTime = sc.formatDT(sc.CreateTime,"yyyy-MM-dd");
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

    /**
     * 打开新增供应商模态框
     */
    sc.addServe = function () {
        sc.clubServe = new Object();
        $("#addClubServe").modal("show");
    }

    /**
     * 打开修改供应商模态框
     * @param e
     */
    sc.editServe = function(e) {
        sc.clubServe = e;
        $("#addClubServe").modal("show");
    }

    /**
     * 打开新增球道模态框
     */
    sc.addFairway=function(){
        sc.currentFairway = new Object();
        $("#addFw").modal("show");
    }

    /**
     * 提交供应商表单
     * @param $valid
     */
    sc.submitServeForm = function ($valid) {
        if($valid){
            sc.saveClubServe();
        }
    }

    /**
     * 保存供应商
     */
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
                // sc.saveTimes(data);
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

    sc.checkDT = function (dt) {
        sc.DateTime = dt
    }

    /**
     * 打开添加时段的模态框
     */
    sc.addDT = function (e) {
        sc.clubServe = e;
        sc.DateTime = null;
        $("#addDateTime").modal("show");
    }


    /**
     * 提交时段
     * @param $valid
     */
    sc.submitDateForm = function ($valid) {
        if($valid){
            sc.saveOtherDate(sc.clubServe)
        }
    }


    sc.rowInit = function () {
        sc.rows = 20;
        sc.Rows = 0;
        sc.TotalOtherDate = 0;
        sc.odKeyword = "";
        sc.loadMore = false;
    }

    /**
     * 加载
     */
    sc.loading = function(){
        if(sc.loadMore) {
            sc.rows += 20;
            sc.odSearch(sc.odKeyword);
        }
    }

    /**
     * 加载动画开关
     */
    sc.isLoading = function (flag) {
        sc.loading = flag;
    }

    /**
     * 搜索日期
     * @param keyword
     */
    sc.odSearch = function (keyword) {
        sc.isLoading(true);
        sc.IsFirstDate = true;//默认第一条日期
        sc.currentTime = new Object();
        sc.DTime = null;
        sc.manIndex(null);
        sc.odKeyword = keyword;
        sc.getGroupDate();
    }

    sc.IsFirstDate = false;//是否选择第一位的日期
    /**
     * 获取特殊时段的时间组
     * @param ClubserveID
     */
    sc.getGroupDate = function () {
        var url = appConfig.url + "Club/getOtherDate";
        var method = 'GET';
        var params = {
            ClubserveID : sc.clubServe.ClubserveID,
            keyword : sc.odKeyword,
            rows : sc.rows,
        }
        var promise = sc.httpParams(url,method,params);
        promise.then(function (data) {
            sc.dateList=new Object();
            if(data.status != 'Error'&&data.data.length > 0){
                sc.dateList = data.data;
                sc.TotalOtherDate = data.count;
                sc.Rows = sc.rows;
                if(sc.IsFirstDate){//选择第一位的日期
                    sc.dateIndex = 0;
                    sc.DTime = sc.dateList[0].DateTime;
                    sc.IsFirstDate = false;
                }
            }

        }),function (data) {
            sc.Load_Failed(data);
        }
        $timeout(function () {
            sc.isLoading(false);
        },1000)
        sc.loadMore = sc.LoadMore(sc.Rows,sc.TotalOtherDate);
    }

    /**
     * 查看时间段的信息
     * @param time
     * @param week
     * @param clubserve
     */
    sc.checkTime = function (time,week,clubserve,$index,e) {
        var Week = week;
        sc.manIndex(e,$index);
        var DateTime = null;
        if(week==='other'){
            DateTime = sc.DTime;
            var dt = new Date(sc.DTime);
            Week = getWeek(dt.getDay());
        }
        var url = appConfig.url + "Club/getPricesByClubserveID";
        var method = 'GET';
        var params = {
            Week : Week,
            Time : time,
            DateTime : DateTime,
            ClubserveID : clubserve.ClubserveID
        }
        var promise = sc.httpParams(url,method,params);
        promise.then(function (data) {
            sc.currentTime = new Object();
            if(data.status == 'Error'){
                sc.currentTime.Time = time;
                sc.currentTime.IsPrivilege = '0';
                sc.currentTime.IsValid = '1';
            }else{
                sc.currentTime = data;
                sc.currentTime.Type = parseInt(data.Type);
            }
        }),function (data) {
            sc.Load_Failed(data);
        }
    }

    sc.saveOtherDate = function (e) {
        var url = appConfig.url + "Club/saveOtherDate";
        var method = 'POST';
        var data = {
            ClubserveID : e.ClubserveID,
            ClubID : e.ClubID,
            DateTime : sc.formatDT(sc.DateTime,"yyyy-MM-dd")
        };
        var promise = sc.httpDataUrl(url,method,data);
        promise.then(function (data) {
            // sc.processResult(data);
            if(data.status=='Success'){
                sc.odSearch('');
                $("#addDateTime").modal("hide");

            }
        }),function (data) {
            sc.Load_Failed(data);
        }
    }


    /**
     * 提交价格
     * @param $valid
     * @param weekday
     */
    sc.priceSubmit = function ($valid,weekday) {
        if($valid){
            sc.currentTime.ClubserveID = sc.clubServe.ClubserveID;
            sc.currentTime.ClubID = sc.clubServe.ClubID;
            if(weekday=='other'){
                sc.otherPriceSave();
            }else{
                sc.currentTime.Week = weekday;
                sc.dailyPriceSave();
            }

        }

    }

    /**
     * 提交日常时段的价格
     * @param $valid
     */
    sc.dailyPriceSave = function () {
        var url = appConfig.url + "Club/dailyPriceSave";
        var method = 'POST';
        var data = sc.currentTime;
        var promise = sc.httpDataUrl(url,method,data);
        promise.then(function (data) {
            sc.processResult(data);
        }),function (data) {
            sc.Load_Failed(data);
        }
    }

    /**
     * 提交other时段的价格
     * @param $valid
     */
    sc.otherPriceSave = function () {
        var dt = new Date(sc.DTime);
        var week = getWeek(dt.getDay());
        sc.currentTime.Week = week;
        sc.currentTime.DateTime = sc.DTime;
        var url = appConfig.url + "Club/saveOtherPrice";
        var method = 'POST';
        var data = sc.currentTime;
        var promise = sc.httpDataUrl(url,method,data);
        promise.then(function (data) {
            sc.processResult(data);
        }),function (data) {
            sc.Load_Failed(data);
        }
    }

    sc.serveIndex = null;
    sc.weekIndex = 0;
    sc.dateIndex = null;
    sc.mTimeIndex = null;
    sc.aTimeIndex = null;
    sc.nTimeIndex = null;

    sc.cnIndex = function (index,e) {
        if(index!=sc.serveIndex){
            sc.currentTime = new Object();
            sc.serveIndex = index;
            sc.IsFirstDate = true;//默认第一条日期
            sc.clubServe = e;
            sc.rowInit();//初始化日期条数
            sc.odSearch('');

        }
    }
    sc.wnIndex = function (index) {
        if(index != sc.weekIndex){
            sc.currentTime = new Object();
            sc.weekIndex = index;
            sc.manIndex(null);
        }

    }

    sc.dtIndex = function (index,date) {
        if(index != sc.dateIndex){
            sc.currentTime = new Object();
            sc.dateIndex = index;
            sc.manIndex(null);
            sc.DTime = date;
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

    /**
     * 获取球道资料
     */
    sc.getFairwayList = function () {
        var club = angular.copy(ClubPageCtrl.clubData);
        var url = appConfig.url + "Club/getClubFairway";
        var method = 'GET';
        var params = {
            ClubID : club.ClubID
        }
        var promise = sc.httpParams(url,method,params);
        promise.then(function (data) {
            sc.fairwayList = data;
        }),function (data) {
            sc.Load_Failed(data);
        }
    }

    //增加球道
    sc.submitFairwayForm = function ($valid) {
        if ($valid) {
            sc.saveFairwayServe();
        }
    }
    /**
     * 保存球道信息
     */
    sc.saveFairwayServe = function () {
        var TotalPar = 0;
        var par = [];
        for(var i=1; i<10;i++){
            var hole = eval("sc.currentFairway.hole"+i);
            par.push(hole);
            TotalPar += parseInt(hole);
        }
        if(TotalPar>36){
            swal({
                title: "球道 " +sc.currentFairway.FairwayName+ " 总杆数不能超出36杆" ,
                text: "当前总杆数为: " +TotalPar +"杆",
                type: "warning"
            })
        }
        var url = appConfig.url + "Club/saveClubFairway";
        var method = "POST";
        var data = {
            ClubID : sc.currentClub.ClubID,
            HoleNum : '9',
            FairwayName : sc.currentFairway.FairwayName,
            Par : par
        }
        var promise = sc.httpDataUrl(url,method,data);
        promise.then(function (data) {
            sc.processResult(data);
            if(data.status == 'Success'){
                sc.getFairwayList();
                $("#addFw").modal("hide");
            }
        }),function (data) {
            sc.Load_Failed(data);
        }
    }

    sc.cPar = 0;

    sc.changePar = function(par){
        sc.cPar = par;
    }

    // sc.tooltipTest =
    //     '<div class="card" style="width: 360px">' +
    //         '<div class="card__header">' +
    //             '<div class="row">' +
    //                 '<h2>标准杆:'+sc.cPar+'</h2>' +
    //             '</div>' +
    //         '</div>' +
    //         '<div class="card__body">' +
    //             '<div class="thumbnail">' +
    //                 '<img src="http://192.168.1.107:8085/GGolfz/rest/file/download/staff_SH_SH@1234.png?t=20170313_150314">' +
    //             '</div>' +
    //         '</div>' +
    //     '</div>'

    var getWeek = function (week) {
        var result = "";
        switch (week){
            case 0:
                result = "日";
                break;
            case 1:
                result = "一";
                break;
            case 2:
                result = "二";
                break;
            case 3:
                result = "三";
                break;
            case 4:
                result = "四";
                break;
            case 5:
                result = "五";
                break;
            case 6:
                result = "六";
                break;
        }
        return result;
    }



    /**
     * 初始化各个时段的信息
     * @param e
     * @param week
     * @param time
     * @constructor
     */
    // sc.saveTimes = function(e){
    //     var timeList = [];
    //     for(var i in sc.weekdays){
    //         if(sc.weekdays[i] != 'other'){
    //             for(var j in sc.dateTimes){
    //                 var data = {
    //                     ClubserveID : e.ClubserveID,
    //                     ClubID : e.ClubID,
    //                     Week : sc.weekdays[i],
    //                     Time : sc.dateTimes[j],
    //                     DownPayment : '500',
    //                     OtherPrice : '0',
    //                     Type : '0',
    //                     IsPrivilege : 0,
    //                     IsDeposit : '0',
    //                 }
    //                 timeList.push(data);
    //             }
    //
    //         }
    //     }
    //     if(timeList.length > 0){
    //         var url = appConfig.url + "Club/createPriceForClubServe";
    //         var method = 'POST';
    //         var data = timeList;
    //         var promise = sc.httpDataUrl(url,method,data);
    //         promise.then(function (data) {
    //             sc.processResult(data);
    //         }),function (data) {
    //             sc.Load_Failed(data);
    //         }
    //     }
    //
    // }

    sc.currentLimitTime = null;
    /**
     * 添加限时活动
     */
    sc.AddLimitTime = function () {
        $("#addLimitTime").modal("show");
    }
}