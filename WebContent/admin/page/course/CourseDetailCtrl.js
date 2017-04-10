var CourseDetailController = function($scope,appConfig){
    var sc = $scope;
    var CoursePageCtrl = sc.$parent;
    sc.currentClub = new Object();
    sc.load = function(){
        sc.currentClub = angular.copy(CoursePageCtrl.clubData);
        console.log(sc.currentClub);
    }
    sc.back = function(){
        CoursePageCtrl.changeCoursePage(1);
    }

    sc.saveClub = function(){

    }

}