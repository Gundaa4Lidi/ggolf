var ClubBookingController = function($scope,$state,$timeout,$interval,$window){
	(function load(){
	    $timeout(function() {
	        fade(document.getElementById('page-loader'));
	    }, 200);
	})()
	function fade(element) {
        var op = 1;  // initial opacity
        var timer = $interval(function () {
            if (op <= 0.1){
                $interval.cancel(timer);
                element.style.display = 'none';
            }
            element.style.opacity = op;
            element.style.filter = 'alpha(opacity=' + op * 100 + ")";
            op -= op * 0.1;
        }, 10);
    }
}