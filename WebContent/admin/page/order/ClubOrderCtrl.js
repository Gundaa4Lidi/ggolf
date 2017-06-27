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
    sc.Rows = 0;
    sc.loadMore = false;
    sc.currentOrder = new Object();
    sc.stateType = {
        ConfirmBall : "2",
        OnlineBooking : "3",
        FinishBooking : "4"
    }

    sc.loading = function(){
        sc.rows += 60;
        sc.getClubOrder();
    }
    sc.load = function () {
        sc.getClubOrder();
    }

    sc.$on('loadClubOrder',function (event,data) {
        sc.load()
    })


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
                sc.Rows = sc.rows;
                sc.loadMore = sc.LoadMore(sc.Rows,sc.TotalOrder);
            }
        }),function (data) {
            sc.Load_Failed(data);
        }

    }

    /**
     * 查看订单
     * @param e
     */
    sc.checkOrder = function(e){
        sc.currentOrder = angular.copy(e);
    }

    /**
     * 现金支付表单提交
     * @param $valid
     */
    sc.submitCashForm = function($valid){
        if($valid){
            sc.CashPayment(sc.currentOrder.OrderID);
        }
    }

    /**
     * 现金支付
     * @param OrderID
     * @constructor
     */
    sc.CashPayment = function (OrderID) {
        var url = appConfig.url + 'ClubOrder/CashPayment';
        var method = 'POST';
        var params = {
            OrderID : OrderID,
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
            if(data.status == 'Success'){
                sc.load();
            }

        }),function (data) {
            sc.Load_Failed(data);
        }

    }

    /**
     * 确认球位
     * @param e
     */
    sc.confirmBall = function (e) {
        sc.currentOrder = angular.copy(e);
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

    /**
     * 申请退款
     * @param e
     */
    sc.applyRefund = function (e) {
        sc.currentOrder = angular.copy(e);
        swal({
            title : "客户申请退款,\n退款理由为:\n"+e.Description+"\n是否同意申请?",
            text : "同意请按是,拒绝请按否",
            type : "warning",
            showCancelButton : true,
            confirmButtonColor : "#DD6B55",
            confirmButtonText : "是",
            cancelButtonText : "否",
            closeOnConfirm : false,
            closeOnCancel : false
        },function (isConfirm) {
            if(isConfirm){
                swal({
                    title : "退款金额为: ¥"+e.DownPayment,
                    text : "确认请按是,否则取消一切操作",
                    type : "warning",
                    showCancelButton : true,
                    confirmButtonColor : "#DD6B55",
                    confirmButtonText : "是",
                    cancelButtonText : "取消",
                    closeOnConfirm : true
                },function (isConfirm) {
                    if(isConfirm){
                        sc.resolveRefund();
                    }
                })
            }else{
                swal({
                    title : "确认拒绝客户的\n退款申请?",
                    text : "确认请按是,否则取消一切操作",
                    type : "warning",
                    showCancelButton : true,
                    confirmButtonColor : "#DD6B55",
                    confirmButtonText : "是",
                    cancelButtonText : "取消",
                    closeOnConfirm : true
                },function (isConfirm) {
                    if(isConfirm){
                        sc.rejectRefund();
                    }
                })
            }
        })
    }

    /**
     * 拒绝退款
     * @constructor
     */
    sc.rejectRefund = function () {
        var url = appConfig.url + "ClubOrder/rejectRefund";
        var method = 'POST';
        var params = {
            OrderID : sc.currentOrder.OrderID
        }
        var promise = sc.httpParams(url,method,params);
        promise.then(function (data) {
            sc.processResult(data);
        }),function (data) {
            sc.Load_Failed(data);
        }
    }

    /**
     * 同意退款
     */
    sc.resolveRefund = function () {
        var url = appConfig.url + "PingPP/Refund";
        var method = 'POST';
        var params = {
            ChargeID : sc.currentOrder.ChargeID,
            amount : sc.currentOrder.DownPayment,
            description : sc.currentOrder.Description
        }
        var promise = sc.httpParams(url,method,params);
        promise.then(function (data) {
            // sc.processResult(data);
            sc.load();
        }),function (data) {
            sc.Load_Failed(data);
        }
    }


}