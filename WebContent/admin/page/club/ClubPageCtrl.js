var ClubPageController = function($scope){
    var sc = $scope;
    sc.clubData = new Object();

    sc.init =function(){
        sc.changeClubPage(1);
    }

    sc.changeClubPage = function(page){
        if(page == 1){
            sc.clubPage = 'page/club/club.html';
            sc.clubData = new Object();
        }else if(page == 2){
            sc.clubPage = 'page/club/clubDetail.html';
        }
    }

    sc.changeClubData = function(e){
        sc.clubData = e;
    }
}