var UserMsgPageController = function($scope){
    $scope.page = 1;
    $scope.UserMsgData = new Object();
    $scope.init = function(){
        $scope.changePage(1);
    }

    $scope.changePage = function(page){
        if(page == 1){
            $scope.userMsgPage = 'page/message/userMsg.html';
            $scope.UserMsgData = new Object();
        }
        if(page == 2){
            $scope.userMsgPage = 'page/message/userMsgData.html';
        }
        console.log(page)
    }

    $scope.changeUserMsgData = function(data){
        $scope.UserMsgData = data;
    }
}