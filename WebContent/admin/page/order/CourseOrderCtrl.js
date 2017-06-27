var CourseOrderController = function ($scope,$timeout,appConfig) {
    var sc = $scope;
    sc.CourseOrderList = null;
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

    sc.load = function () {
        sc.CourseOrderList = null;
    }
    
    sc.loading = function () {
        sc.rows += 60;
        sc.getCourseOrder();
    }

    sc.getCourseOrder = function () {
        var url = appConfig.url + "CourseOrder/GETCourseOrder";
        var method ='GET';
        var params = {
            keyword : sc.keyword,
            rows : sc.rows
        }
        var promise = sc.httpParams(url,method,params);
        promise.then(function (data) {
            if(data.status != 'Error'){
                sc.TotalOrder = data.count;
                sc.orderList = data.courseOrders;
                sc.Rows = sc.rows;
                sc.loadMore = sc.LoadMore(sc.Rows,sc.TotalOrder);
            }
        }),function (data) {
            sc.Load_Failed(data);
        }
    }

    sc.checkCourseOrder = function (e) {
        sc.currentOrder = angular.copy(e);
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
        var url = appConfig.url + "CourseOrder/rejectRefund";
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