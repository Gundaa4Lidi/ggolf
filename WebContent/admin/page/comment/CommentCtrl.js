var CommentController = function($scope,appConfig){
	var sc = $scope;
	sc.CommentList = [];
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
    sc.rows = 15;
    sc.Rows = 0;
    sc.loadMore = false;
    sc.TotalMessage = 0;

    sc.load = function () {
        sc.getCommentList();
    }

    sc.loading = function(){
        if(sc.loadMore) {
            sc.rows += 15;
            sc.getCommentList();
        }
    }

    // sc.$watch('Rows',function(newValue){
    //     if(newValue < sc.TotalMessage){
    //         sc.loadMore = true;
    //     }else if(newValue >= sc.TotalMessage){
    //         sc.loadMore = false;
    //     }
    // })

	sc.getCommentList = function () {
		var url = appConfig.url + 'Comment/getAll';
		var method = 'GET';
		var params = {
			days : sc.filterDay,
            keyword : sc.keyword,
            rows : sc.rows+''
		}
        var promise = sc.httpParams(url,method,params);
        promise.then(function(data){
            console.log(data);
            sc.TotalMessage = data.count;
            sc.CommentList = data.Data;
            sc.Rows = sc.rows;
            sc.loadMore = sc.LoadMore(sc.Rows,sc.TotalMessage);
        }),(function(data){
            sc.Load_Failed(data);
        })

    }
	
	sc.itemClass = [
		"activity-log__content--lead",
		"activity-log__content--contact",
		"activity-log__content--task",
		"activity-log__content--notes",
		"activity-log__content--calendar",
		"activity-log__content--qa",
		"activity-log__content--message",
	]
	
}