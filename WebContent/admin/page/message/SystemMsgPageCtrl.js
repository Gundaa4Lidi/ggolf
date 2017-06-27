var SystemPageController = function($scope){
    $scope.page = 1;
    $scope.SystemMsgData = new Object();
    $scope.init = function(){
        $scope.changePage(1);
    }
    $scope.rows = 15;
    $scope.keyword = '';
    $scope.filterDays = [
        {key:"默认",   id:"0",value:0},
        {key:"最近7天", id:"1",value:7},
        {key:"最近30天",id:"2",value:30},
        {key:"最近60天",id:"3",value:60},
        {key:"最近90天",id:"4",value:90},
        {key:"最近180天",id:"5",value:180}
    ]
    $scope.filterDay = $scope.filterDays[0].value;

    $scope.changePage = function(page){
        if(page == 1){
            $scope.systemPage = 'page/message/systemMsg.html';
            $scope.SystemMsgData = new Object();
        }
        if(page == 2){
            $scope.systemPage = 'page/message/systemMsgData.html';
        }

    }
    $scope.$on('SysMsgConfig',function (event,data) {
        if(data.keyword&&data.keyword!=''){
            $scope.keyword = data.keyword;
        }
        if(data.rows){
            $scope.rows = data.rows;
        }
        if(data.filterDay){
            $scope.filterDay = data.filterDay;
        }
    })


    $scope.changeSysMsgData = function(data){
        $scope.SystemMsgData = data;
    }


}
