var ClubPageController = function($scope){
    var sc = $scope;
    sc.clubData = new Object();
    sc.isHot = false;
    sc.isTop = false;
    sc.isClub = true;
    sc.keyword = '';
    sc.rows = 20;

    sc.init =function(){
        sc.changeClubPage(1);
    }

    sc.$on('ClubConfig',function (event,data) {
        if(data.keyword&&data.keyword!=''){
            sc.keyword = data.keyword;
        }
        if(data.rows){
            sc.rows = data.rows;
        }
    })

    sc.changeClubPage = function(page){
        if(page == 1){
            // console.log("isHot:"+sc.isHot,"isTop:"+sc.isTop,"isClub:"+sc.isClub);
            sc.clubPage = 'page/club/club.html';
            sc.clubData = new Object();
        }else if(page == 2){
            // console.log("isHot:"+sc.isHot,"isTop:"+sc.isTop,"isClub:"+sc.isClub);
            sc.clubPage = 'page/club/clubDetail.html';
        }
    }

    sc.changeClubData = function(e){
        sc.clubData = e;
    }

    sc.setIsHot = function () {
        sc.isHot = !sc.isHot;
    }
    sc.getIsHot = function () {
        return sc.isHot;
    }
    sc.setIsTop = function () {
        sc.isTop = !sc.isTop;
    }
    sc.getIsTop = function () {
        return sc.isClub;
    }
    sc.setIsClub = function () {
        sc.isClub = !sc.isClub;
    }
    sc.getIsClub = function () {
        return sc.isClub;
    }




}