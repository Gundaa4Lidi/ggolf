var UserMsgDataController = function($scope,$rootScope,$window,Upload,$timeout,appConfig){
    var sc = $scope;
    var UserPageCtrl = sc.$parent;
    sc.criticsName = $window.sessionStorage.Staffname;
    sc.criticsHead = $window.sessionStorage.StaffHead;
    sc.criticsID   = $window.sessionStorage.StaffId;
    sc.commentData = null;
    sc.SystemMsgData = null;
    sc.rows = 5;
    sc.Rows = 0;
    sc.replyRows = 5;
    sc.write = false;
    sc.reply = false;
    sc.packUp = false;
    sc.loadMore = false;
    sc.TotalComment = 0;
    sc.currentComment = null;
    sc.commentIndex = null;

    sc.back = function(){
        UserPageCtrl.changePage(1);
    }

    sc.load = function(){
        sc.UserMsgData = angular.copy(UserPageCtrl.UserMsgData);
        sc.getComment();
    }

    sc.Write = function(){
        sc.write = true;
    }

    /**
     * 监听加载数量是否超出当前评论的数量
     */
    sc.$watch('Rows',function(newValue){
        if(newValue< sc.TotalComment){
            sc.loadMore = true;
        }else if(newValue >= sc.TotalComment){
            sc.loadMore = false;
        }
    });
    /**
     * 点击回复或添加评论弹出评论编辑器
     * @param e
     * @constructor
     */
    sc.Reply = function(index,e){
        sc.currentComment = e;
        sc.reply = true;
        sc.setCommentIndex(index,e);
        $timeout(function(){
            $("#textFocus").focus();
        },200);
        var memo = "@" + e.UserName + ":";
        sc.currentComment.currentMemo = memo;
    }

    /**
     * 设置当前评论标识
     * @param index
     * @param e
     */
    sc.setCommentIndex = function(index,e){
        sc.commentIndex = {
            index: index,
            comment : e
        }
    }
    /**
     * 加载5条回复
     * @param index
     * @param e
     */
    sc.loadReply = function(index,e){
        sc.currentComment = e;
        if(sc.commentIndex!=null && sc.commentIndex.index!=index){//收起其他评论的回复
            // sc.pack_up(sc.commentIndex.index,sc.commentIndex.comment);
            sc.replyRows = 5;
            sc.packUp = false;
            sc.getReplyList(sc.commentIndex.index,sc.commentIndex.comment);
        }
        sc.setCommentIndex(index,e);
        sc.packUp = true;
        sc.replyRows += 5 ;
        sc.getReplyList(index,e);
        var target = "#addComment"+index+"";
        $("body").animate({ scrollTop: $(target).offset().top - 500},"slow");
    }

    /**
     * 收起回复
     * @param index
     * @param e
     */
    sc.pack_up = function(index,e){
        sc.replyRows = 5;
        sc.packUp = false;
        sc.getReplyList(index,e);
        var target = "#comment"+index+"";
        $("body").animate({ scrollTop: $(target).offset().top - 150},800);
    }

    /**
     * 加载5条评论
     */
    sc.loading = function(){
        sc.rows +=5;
        sc.getComment();
    }
    /**
     * 取消回复
     */
    sc.cancelReply = function(){
        sc.reply = false;
        sc.currentComment.currentMemo = null;
    }

    /**
     * 取消评论
     */
    sc.cancelComment = function(){
        sc.write = false;
        sc.writeComment = null;
    }

    /**
     * 加载回复数据
     * @param index
     * @param e
     */
    sc.getReplyList = function(index,e){
        console.log(e)
        var url = appConfig.url + "Message/getReplyList";
        var method = 'GET';
        var params = {
            rows : sc.replyRows,
            UserID : e.UserID,
            CommentID : e.CommentID
        }
        var promise = sc.httpParams(url,method,params);
        promise.then(function (data) {
            if(!sc.commentData.comment[index].replyData){
                sc.commentData.comment[index].replyData = new Object();
            }
            sc.commentData.comment[index].replyData.comment = data.comment;
            sc.commentData.comment[index].replyData.count = data.count;
        }),function(data){
            sc.Load_Failed(data);
        }
    }

    /**
     * 获取当前信息的评论列表
     */
    sc.getComment = function(){
        var msg = sc.UserMsgData;
        var url = appConfig.url + 'Message/getCommentByMsgID';
        var method = 'GET';
        var params = {
            rows : sc.rows+'',
            MsgID : msg.MsgID,
            Type : msg.Type
        }
        var promise = sc.httpParams(url,method,params);
        promise.then(function(data){
            sc.commentData = data;
            sc.TotalComment = data.CommentCount;
            sc.Rows = sc.rows;
        }),function(data){
            sc.Load_Failed(data);
        }

    }

    /**
     * 点赞
     * @param e
     */
    sc.likes = function(e){
        var url = appConfig.url + 'Message/likeForComment';
        var method = 'POST';
        var data = {
            UserID : $window.sessionStorage.StaffId,
            ThemeID : e.CommentID,
            Type : e.Action
        }
        var promise = sc.httpDataUrl(url,method,data);
        promise.then(function(data){
            if(data.status == '你已点赞'){
                swal("你已点赞","不用重复点赞","warning")
            }
            sc.getComment()
        }),function(data){
            sc.Load_Failed(data);
        }
    }

    /**
     * 发送评论
     */
    sc.sendComment = function(){
        if(sc.writeComment===undefined){
            swal("回复内容不能为空","请重新输入!","warning");
        }else{
            var msg = sc.UserMsgData;
            var url = appConfig.url + 'Message/saveComment';
            var method = 'POST';
            var data = {
                Action : 'comment',
                RootType : msg.Type,
                RootID   : msg.MsgID,
                UserName : sc.criticsName,
                UserHead : sc.criticsHead,
                UserID   : sc.criticsID,
                ParentType : 'dynamic',
                ParentID : msg.MsgID,
                ParentUserID : msg.SenderID,
                Memo     : sc.writeComment
            };
            var promise = sc.httpDataUrl(url,method,data);
            promise.then(function(data){
                sc.processResult(data);
                sc.cancelComment();
                sc.getComment()
            }),function(data){
                sc.Load_Failed(data);
            }
        }

    }

    /**
     * 发送回复
     */
    sc.sendReply = function(index,e){
        var comment = sc.currentComment;
        var parentID = comment.CommentID;
        var parentUserID = comment.UserID;
        var Memo = sc.currentComment.currentMemo;
        if(comment.Action == 'reply'){
            parentID = comment.ParentID;
            parentUserID = comment.ParentUserID;
        }
        if(Memo.indexOf("@" + comment.UserName + ":")!=-1){//这里如果用户手贱的回删条件中的字符,就无法分割出来
            Memo = Memo.split(":")[1];
        }
        if(Memo===undefined||Memo === ''){
            swal("回复内容不能为空","请重新输入!","warning");
            // sc.Reply(sc.currentComment);
        }else{
            sc.currentComment.currentMemo = Memo;
            var url = appConfig.url + 'Message/saveComment';
            var method = 'POST';
            var data = {
                Action : 'reply',
                RootType : comment.RootType,
                RootID   : comment.RootID,
                UserName : sc.criticsName,
                UserHead : sc.criticsHead,
                UserID   : sc.criticsID,
                ParentType : 'user',
                ParentID : parentID,
                ParentUserID : parentUserID,
                Memo     : sc.currentComment.currentMemo,
                ReplyID  : comment.UserID,
                ReplyName: comment.UserName
            };
            var promise = sc.httpDataUrl(url,method,data);
            promise.then(function(data){
                sc.processResult(data);
                sc.cancelReply();
                sc.getReplyList(index,e);
            }),function(data){
                sc.Load_Failed(data);
            }
        }
    }
    /**
     * 删除评论
     * @param e
     */
    sc.remove = function(e){
        var url = appConfig.url + 'Message/removeComment';
        var method = 'POST';
        var params = {
            CommentID : e.CommentID
        }
        var promise = sc.httpParams(url,method,params);
        promise.then(function(data){
            sc.processResult(data);
            sc.getComment();
        }),function(data){
            sc.Load_Failed(data);
        }

    }

}