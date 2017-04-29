var UserMsgController = function($scope,$rootScope,$http,appConfig,$q,$window,Upload,$timeout){
    var sc = $scope;
    var UserMsgPageCtrl = sc.$parent;
    sc.currentMsg = null;
    sc.rows = 15;
    sc.Rows = 0;
    sc.MsgLists = [];
    sc.loadMore = false;
    sc.TotalMessage = 0;
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
    sc.searchChange = function () {
        this.getMsgList();
    }
    sc.load = function(){
        this.searchChange();
    }

    sc.$watch('Rows',function(newValue){
        if(newValue < sc.TotalMessage){
            sc.loadMore = true;
        }else if(newValue >= sc.TotalMessage){
            sc.loadMore = false;
        }
    })

    sc.loading = function(){
        sc.rows += 15;
        sc.searchChange();
    }

    sc.scrollTo = function(){
        var scrollToTarget = 'html';
        var scrollToOffset = 0;
        $('html, body').animate({
            scrollTop: ($(scrollToTarget).offset().top) - scrollToOffset
        }, 500);
    }

    sc.getMsgList = function(){
		/*var url = appConfig.url + 'Article/getAllArticle';*/
        var url = appConfig.url + 'Message/getMessages' + sc.filterDay;
        var method = 'GET';
        var params = {
            keyword : sc.keyword,
            rows : sc.rows+'',
            type : appConfig.MSG_TYPE_NO_SYS
        }
        var promise = sc.httpParams(url,method,params);
        promise.then(function(data){
            sc.TotalMessage = data.count;
            sc.MsgList = data.Data;
            sc.Rows = sc.rows;
        }),(function(data){
            sc.Load_Failed(data);
        })
    }

    sc.addMsg = function(){
        sc.currentMsg = new Object();
    }

    sc.check = function(e){
        UserMsgPageCtrl.changeUserMsgData(e);
        UserMsgPageCtrl.changePage(2);
    }

    sc.remove = function(e){
        var obj = new Object();
        if(e.MsgID){
            obj.Title = "删除\n"+e.Title+"?";
            obj.Text = "删除之后将无法恢复!";
            obj.MsgID = e.MsgID;
            this.removeMsg(obj);
        }
    }

    sc.removeMsg = function(removeObj){
        swal({
            title : removeObj.Title,
            text : removeObj.Text,
            type : "warning",
            showCancelButton : true,
            confirmButtonColor : "#DD6B55",
            confirmButtonText : "是的",
            cancelButtonText : "取消",
            closeOnConfirm : false
        }, function() {
            var url = appConfig.url + "Message/removeMsg";
            var method = 'POST';
            var params = {
                MsgID : removeObj.MsgID
            }
            var promise = sc.httpParams(url,method,params);
            promise.then(function(data){
                sc.processResult(data);
                sc.load();
            }),(function(data){
                sc.Load_Failed(data);
            })
        });
    }

    sc.saveMsg = function(){
        if(!sc.currentMsg.Title){
            swal("请填写消息标题!","","warning");
        }else if(!sc.currentMsg.Details){
            swal("请填写消息内容!","","warning");
        }else{
            sc.currentMsg.SenderID = $window.sessionStorage.StaffId;
            sc.currentMsg.SenderName = $window.sessionStorage.Staffname;
            sc.currentMsg.SenderPhoto = $window.sessionStorage.StaffHead;
            sc.currentMsg.Type = appConfig.MSG_TYPE_DYNAMIC;
            var url = appConfig.url + "Message/saveMessage";
            var method = 'POST';
            var data = sc.currentMsg;
            var promise = sc.httpDataUrl(url,method,data);
            promise.then(function(data){
                sc.processResult(data);
                sc.load();
                $("#new-message").modal("hide");
            }),(function(data){
                sc.Load_Failed(data);
            })

        }

    }
}