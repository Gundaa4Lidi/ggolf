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

    sc.address = function(e){
        if(!e){
            sc.p = null;
            sc.c = null;
            sc.a = null;
            sc.d = null;
        }else{
            if(e.Province){
                sc.p = e.Province;
            }
            if(e.City){
                sc.c = e.City;
            }
            if(e.Area){
                sc.a = e.Area;
            }
            if(e.ClubAddress){
                sc.d = e.ClubAddress;
            }
        }
        return sc.d;
    }

}