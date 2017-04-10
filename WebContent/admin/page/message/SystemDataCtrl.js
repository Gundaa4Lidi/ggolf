var SystemDataController = function($scope,$rootScope,$window,appConfig){
    var sc = $scope;
    var SystemPageCtrl = sc.$parent;
    sc.commentHead = $window.sessionStorage.StaffHead;
    sc.back = function(){
        SystemPageCtrl.changePage(1);
    }
    sc.SystemMsgData = null;
    sc.write = false;

    sc.load = function(){
        sc.SystemMsgData = angular.copy(SystemPageCtrl.SystemMsgData);
        console.log(sc.SystemMsgData)
    }

    sc.Write = function(){
        sc.write = true;
    }
    sc.cancelComment = function(){
        sc.write = false;
        console.log(sc.writeComment)
        sc.writeComment = null;
    }

    sc.sendComment = function(){
        var msg = sc.SystemMsgData;
        var url = appConfig.url + 'Message/saveComment';
        var method = 'POST';
        var data = {
            Action : 'comment',
            RootType : msg.Type,
            RootID   : msg.MsgID,
            UserName : $window.sessionStorage.Staffname,
            UserHead : $window.sessionStorage.StaffHead,
            UserID   : $window.sessionStorage.StaffId,
            ParentType : 'dynamic',
            ParentID : msg.MsgID,
            ParentUserID : msg.SenderID,
            Memo     : sc.writeComment
        };
        var promise = sc.httpDataUrl(url,method,data);
        promise.then(function(data){
            sc.cancelComment();
        }),function(data){
            swal("加载失败",data,"warning");
        }

    }



}