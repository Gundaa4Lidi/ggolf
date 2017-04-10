var SystemPageController = function($scope){
    $scope.page = 1;
    $scope.SystemMsgData = new Object();
    $scope.init = function(){
        $scope.changePage(1);
    }

    $scope.changePage = function(page){
        if(page == 1){
            $scope.systemPage = 'page/message/systemMsg.html';
            $scope.SystemMsgData = new Object();
        }
        if(page == 2){
            $scope.systemPage = 'page/message/systemMsgData.html';
        }

    }

    $scope.changeSysMsgData = function(data){
        $scope.SystemMsgData = data;
    }


}
