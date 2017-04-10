var ClubDetailController = function($scope,appConfig){
    var sc = $scope;
    var ClubPageCtrl = sc.$parent;
    sc.currentClub = new Object();
    sc.load = function(){
        sc.currentClub = angular.copy(ClubPageCtrl.clubData);
        console.log(sc.currentClub);
    }
    sc.back = function(){
        ClubPageCtrl.changeClubPage(1);
    }

    sc.saveClub = function(){
    }

}