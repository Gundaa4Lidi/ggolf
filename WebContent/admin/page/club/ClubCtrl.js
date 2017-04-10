var ClubController = function($scope,$http,appConfig,$window,Upload,$timeout){
	var sc = $scope;
	var ClubPageCtrl = sc.$parent;
    sc.rows = 20;
    sc.Rows = 0;
    sc.TotalClub = 0;
	sc.currentItem = null;
    sc.currentClub = new Object();
    sc.clubList = null;
    sc.edit = false;
    sc.loadMore = false;
    sc.myInterval = 'none';
    sc.noWrapSlides = false;
    sc.pause = true;
    sc.active = 0;

	sc.loading = function(){
		sc.rows += 20;
		sc.getClubList();
	}
	sc.$watch('Rows',function(e){
		if(e < sc.TotalClub){
			sc.loadMore = true;
		}else if(e >= sc.TotalClub){
			sc.loadMore = false;
		}
	})

	sc.load=function(){
		sc.getClubList();
	}

	sc.submitForm = function(isValid){
		if(isValid){
			if(sc.p){
				sc.currentClub.Province = sc.p;
			}
			if(sc.c){
				sc.currentClub.City = sc.c;
			}
			if(sc.a){
                sc.currentClub.Area = sc.a;
			}
			sc.currentClub.ClubType = '球场';
			var url = appConfig.url + 'Club/saveClub';
			var method = 'POST';
			var data = sc.currentClub;
			var promise = sc.httpDataUrl(url,method,data);
			promise.then(function (data) {
				sc.processResult(data);
				$('#new-club').modal("hide");
                sc.getClubList();
            }),function(data){
				sc.Load_Failed(data);
			}
		}
	}

	sc.getClubList = function(){
		var url = appConfig.url + 'Club/getClubByKeyword';
		var method = 'GET';
		var params = {
			keyword : sc.keyword,
			rows : sc.rows,
			clubType : '球场'
		};
		var promise = sc.httpParams(url,method,params);
		promise.then(function(data){
			sc.clubList = data.data;
			sc.TotalClub = data.pageCount;
			sc.Rows = sc.rows;
		}),function(data){
			sc.Load_Failed(data);
		}
	}


	sc.checkClub = function(obj){
        ClubPageCtrl.changeClubData(obj);
        ClubPageCtrl.changeClubPage(2);
	}

    sc.upload = function (files) {
        if (files && files.length) {
            for (var i = 0; i < files.length; i++) {
                var file = files[i];
                Upload.upload({
                    url: '/GGolfz/rest/file/upload',
                    file: file
                }).progress(function (evt) {
                    var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
                    console.log('progress: ' + progressPercentage + '% ' + evt.config.file.name);
                }).success(function (data) {
                    sc.currentClub.ClubPhoto.push(data);
                }).error(function (data) {
                    // console.log(data)
                    alert('上传失败');
                })
            }
        }
    };

    sc.addClub = function () {
        sc.currentClub = new Object();
        sc.currentClub.ClubPhoto = [];
        sc.address();
        $('#new-club').modal('show');
    }

    sc.removeClub = function(e){
        swal({
            title : "删除\n"+e.ClubName+"?",
            text : "",
            type : "warning",
            showCancelButton : true,
            confirmButtonColor : "#DD6B55",
            confirmButtonText : "是的",
            cancelButtonText : "取消",
            closeOnConfirm : false
        }, function() {
            var url = appConfig.url + 'Club/removeClub';
            var method = 'POST';
            var params = {
                ClubID : e.ClubID
            }
            var promise = sc.httpParams(url,method,params);
            promise.then(function (data) {
                sc.processResult(data)
                sc.load();
            }),function (data) {
                sc.Load_Failed(data);
            }
        });
    }

    sc.selectImg = function (index) {
        $timeout.cancel(sc.timeout);
        sc.timeout = $timeout(function () {
            sc.active = index;
        },200);

    }

    sc.deleteImg = function(index){
        sc.currentClub.ClubPhoto.splice(index,1);
    }
    sc.openItem = function(e){
        sc.currentClub = e;
        sc.address(e);
        $('#new-club').modal("show");
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