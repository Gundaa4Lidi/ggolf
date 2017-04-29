var CommentController = function($scope,appConfig){
	var sc = $scope;
	sc.CommentList = null;

	sc.getComment = function () {
		var url = appConfig.url + ''
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