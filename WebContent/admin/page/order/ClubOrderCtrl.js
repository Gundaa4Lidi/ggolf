var ClubOrderController = function ($scope,appConfig) {
    var sc = $scope;
    sc.filterDays = [
        {key:"默认",   id:"0",value:0},
        {key:"最近7天", id:"1",value:7},
        {key:"最近30天",id:"2",value:30},
        {key:"最近60天",id:"3",value:60},
        {key:"最近90天",id:"4",value:90},
        {key:"最近180天",id:"5",value:180}
    ]
    sc.filterDay = sc.filterDays[0].value;
    sc.TotalOrder = 0;
    sc.rows = 60;
    sc.currentOrder = new Object();
    sc.stateType = {
        ConfirmBall : "2",
        OnlineBooking : "3",
        FinishBooking : "4"
    }


    var data = [
        {OrderID :"100001", UserID :"23", ClubID :"26", ClubName :"大地宫宇", ClubserveID :"12", ClubserveName :"咔够", ClubserveLimitTimeID :"4567812345", ClubservePriceID :"33", State :"提交订单", CreateTime :"2017-04-20", Type :"全额支付", DownPayment :"5000", PayBillorNot :"", StartDate :"2017-04-22", StartTime :"07:30", Names :"金馆长,苦笑,提百万", Tel :"13334878756", ServiceExplain :"18洞果岭/僮/车/柜",},
        {OrderID :"100001", UserID :"23", ClubID :"26", ClubName :"大地宫宇", ClubserveID :"12", ClubserveName :"咔够", ClubserveLimitTimeID :"4567812345", ClubservePriceID :"33", State :"确认球位", CreateTime :"2017-04-20", Type :"球场现付", DownPayment :"5000", PayBillorNot :"", StartDate :"2017-04-22", StartTime :"07:30", Names :"金馆长,苦笑,提百万", Tel :"13334878756", ServiceExplain :"18洞果岭/僮/车/柜",},
        {OrderID :"100001", UserID :"23", ClubID :"26", ClubName :"大地宫宇", ClubserveID :"12", ClubserveName :"咔够", ClubserveLimitTimeID :"4567812345", ClubservePriceID :"33", State :"提交订单", CreateTime :"2017-04-20", Type :"全额支付", DownPayment :"5000", PayBillorNot :"", StartDate :"2017-04-22", StartTime :"07:30", Names :"金馆长,苦笑,提百万", Tel :"13334878756", ServiceExplain :"18洞果岭/僮/车/柜",},
    ]
    sc.orderList = data;

    sc.loading = function(){
        sc.rows += 60;
        sc.getClubOrder();
    }
    sc.load = function () {
        sc.getClubOrder();
    }

    sc.getClubOrder = function () {
        var url = appConfig.url + 'ClubOrder/getClubOrder' + sc.filterDay;
        var method = 'GET';
        var params = {
            keyword : sc.keyword,
            rows : sc.rows
        }
        var promise = sc.httpParams(url,method,params);
        promise.then(function (data) {
            if(data.status != 'Error'){
                sc.TotalOrder = data.count;
                sc.orderList = data.clubOrders;
            }
        }),function (data) {
            sc.Load_Failed(data);
        }

    }

    sc.checkOrder = function(e){
        sc.currentOrder = e;
    }

    sc.submitCashForm = function($valid){
        if($valid){
            sc.updateOrder(sc.stateType.FinishBooking,sc.currentOrder.OrderID);
        }
    }

    /**
     * 修改订单状态
     * @param StateType
     * @param OrderID
     */
    sc.updateOrder = function(StateType,OrderID){
        var url = appConfig.url + 'ClubOrder/updateOrderState';
        var method = 'POST';
        var params = {
            StateType : StateType,
            OrderID : OrderID
        }
        var promise = sc.httpParams(url,method,params);
        promise.then(function (data) {
            sc.processResult(data);
            sc.load();
        }),function (data) {
            sc.Load_Failed(data);
        }

    }

    sc.confirmBall = function (e) {
        sc.currentOrder = e;
        swal({
            title : "是否已找到适合的球位?",
            text : "",
            type : "warning",
            showCancelButton : true,
            confirmButtonColor : "#DD6B55",
            confirmButtonText : "是的",
            cancelButtonText : "取消",
            closeOnConfirm : false
        },function () {
            sc.updateOrder(sc.stateType.ConfirmBall,e.OrderID)
        })
    }



}