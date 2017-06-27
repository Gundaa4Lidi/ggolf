var SystemMsgController = function($scope,$rootScope,$http,$q,appConfig,$window,Upload,$timeout){
	var sc = $scope;
    var SystemPageCtrl = sc.$parent;
    sc.currentMsg = null;
    // sc.rows = 15;
    sc.Rows = 0;
    sc.MsgLists = [];
    sc.TotalMessage = 0;
    sc.loadMore = false;
    sc.isPush = false;
    // sc.filterDays = [
		// {key:"默认",   id:"0",value:0},
		// {key:"最近7天", id:"1",value:7},
    //     {key:"最近30天",id:"2",value:30},
    //     {key:"最近60天",id:"3",value:60},
    //     {key:"最近90天",id:"4",value:90},
    //     {key:"最近180天",id:"5",value:180}
    // ]
    // sc.filterDay = sc.filterDays[0].value;
    sc.filterDaySelect = {
    	width:"122"
    }
	sc.searchChange = function () {
		sc.getMsgList();
    }
	sc.load = function(){
        sc.searchChange();
	}
    sc.SysMsgConfig = {
        keyword : sc.keyword,
        rows: sc.rows,
        filterDay : sc.filterDay
    }
    sc.changeConfig = function (flag) {
        switch(flag){
            case 1:
                sc.SysMsgConfig.keyword = sc.keyword;
                break;
            case 2:
                sc.SysMsgConfig.rows = sc.rows;
                break;
            case 3:
                sc.SysMsgConfig.filterDay = sc.filterDay;
                break;
            default:
                break;
        }
        sc.$emit('SysMsgConfig',sc.SysMsgConfig);
    }

	sc.loading = function(){
        if(sc.loadMore) {
            SystemPageCtrl.rows += 15;
            sc.searchChange();
        }
    }

    // sc.scrollTo = function(){
    //     var scrollToTarget = 'html';
    //     var scrollToOffset = 0;
    //     $('html, body').animate({
    //         scrollTop: ($(scrollToTarget).offset().top) - scrollToOffset
    //     }, 500);
    // }
    sc.Cancel = function () {
        $("#new-message").modal("hide");
        sc.isPush = false;
    }

	sc.getMsgList = function(){
		/*var url = appConfig.url + 'Article/getAllArticle';*/
        var url = appConfig.url + 'Message/getMessages' + sc.filterDay;
        var method = 'GET';
        var params = {
            keyword : SystemPageCtrl.keyword,
            rows : SystemPageCtrl.rows+'',
            type : appConfig.MSG_TYPE_SYS
        }
        var promise = sc.httpParams(url,method,params);
        promise.then(function(data){
            sc.TotalMessage = data.count;
            sc.MsgList = data.Data;
            sc.Rows = SystemPageCtrl.rows;
            sc.loadMore = sc.LoadMore(sc.Rows,sc.TotalMessage);
        }),(function(data){
            sc.Load_Failed(data);
        })

	}

	sc.addMsg = function(){
		sc.currentMsg = new Object();
		$("#new-message").modal("show");
	}

	sc.check = function(e){
		SystemPageCtrl.changeSysMsgData(e);
		SystemPageCtrl.changePage(2);
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

	sc.saveSysMsg = function(){
		if(!sc.currentMsg.Title){
            swal("请填写消息标题!","","warning");
		}else if(!sc.currentMsg.Details){
            swal("请填写消息内容!","","warning");
		}else{
            sc.currentMsg.SenderID = $window.sessionStorage.StaffId;
            sc.currentMsg.SenderName = $window.sessionStorage.Staffname;
            sc.currentMsg.SenderPhoto = $window.sessionStorage.StaffHead;
            sc.currentMsg.Type = appConfig.MSG_TYPE_SYS;
            if(sc.isPush){
                sc.currentMsg.ReleaseOrNot = 'Y';
                sc.isPush = false;
            }
            var url = appConfig.url + "Message/saveMessage";
            var method = 'POST';
            var data = sc.currentMsg;
            var promise = sc.httpDataUrl(url,method,data);
            promise.then(function(data){
                sc.processResult(data);
                if(data.status != "Error"){
                    sc.load();
                    $("#new-message").modal("hide");
                }
            }),(function(data){
                sc.Load_Failed(data);
            })

        }

	}

	sc.Push = function (e) {
        sc.currentMsg = e;
        sc.isPush = true;
        $("#new-message").modal("show");
    }

	sc.sendBroadcast = function () {
        var url = appConfig.url + "Message/PushCast";
        var method = "POST";
        var data = {
            ticker: sc.currentMsg.Title,
            title: sc.currentMsg.Title,
            text: sc.currentMsg.Details,
            type: "broadcast",
            display_type: "notification",
            after_open: "go_app",
            description: "系统通知",
        }
        var promise = sc.httpDataUrl(url,method,data);
        promise.then(function (data) {
            if(data.errorMsg){
                swal("发送失败",data.errorMsg,"warning");
            }else{
                sc.saveSysMsg();
            }
        }),function (data) {
            sc.Load_Failed(data);
        }
    }

}