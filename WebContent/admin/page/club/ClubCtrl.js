var ClubController = function($scope,$http,appConfig,$window,$q,Upload,$timeout,cities){
	var sc = $scope;
	var ClubPageCtrl = sc.$parent;
	sc.currentItem = null;
    sc.currentClub = new Object();
    sc.clubList = null;
    sc.edit = false;
    sc.myInterval = 'none';
    sc.noWrapSlides = false;
    sc.pause = true;
    sc.active = 0;
    sc.rows = 20;
    sc.Rows = 0;
    sc.TotalClub = 0;
    sc.loadMore = false;

	sc.loading = function(){
        if(sc.loadMore) {
            sc.rows += 20;
            sc.getClubList();
        }
	}
	// sc.$watch('Rows',function(e){
	// 	if(e < sc.TotalClub){
	// 		sc.loadMore = true;
	// 	}else if(e >= sc.TotalClub){
	// 		sc.loadMore = false;
	// 	}
	// })

	sc.load=function(){
		sc.getClubList();
        sc.getProvince();
	}

	sc.openModal = function(flag){
        switch(flag){
            case 1:
                $("#new-club").modal("hide");
                $("#cut_image").modal("show");
                break;
            case 2:
                $("#cut_image").modal("hide");
                $("#new-club").modal("show");
                break;
            case 3:
                $("#new-club").modal("hide");
                $("#add_address").modal("show");
                break;
            case 4:
                $("#add_address").modal("hide");
                $("#new-club").modal("show");
                break;
            default:
                break;
        }
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
			if(sc.d){
			    sc.currentClub.ClubAddress = sc.d;
            }
            if(!sc.p || !sc.c || !sc.d){
			    swal("请确认地址是否填写正确!","","warning");
            }else{
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
		}else{
		    swal("表单验证失败","","warning");
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
			sc.TotalClub = data.count;
			sc.Rows = sc.rows;
		}),function(data){
			sc.Load_Failed(data);
		}
        sc.loadMore = sc.LoadMore(sc.Rows,sc.TotalClub);
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

    sc.logoImage = '';
    sc.logoCroppedImage = '';

    sc.logoSave = function(e){
        sc.currentClub.Logo = e;
        sc.openModal(false);
    }

    sc.logoSelect = function (files) {
        if(files && files.length){
            for(var i = 0; i < files.length; i++){
                var file = files[i];
                var reader = new FileReader();
                reader.onload = function(evt) {
                    console.log("1")
                    sc.$apply(function (sc) {
                        sc.logoImage = evt.target.result;
                        // console.log("类型："+typeof sc.logoImage,"值:"+sc.logoImage)
                    })
                }
                reader.readAsDataURL(file);
            }
        }
    };

    sc.addClub = function () {
        sc.currentClub = new Object();
        sc.currentClub.ClubPhoto = [];
        sc.currentClub.IsHot = '0';
        sc.currentClub.IsTop = '0';
        sc.address();
        sc.openModal(2);
    }

    sc.openItem = function(e){
        sc.currentClub = angular.copy(e);
        console.log("IsHot:"+e.IsHot,"IsTop:"+e.IsTop);
        sc.address(e);
        $('#new-club').modal("show");
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

    sc.importCity = function () {
        var arr = [];
        var flag = true;
        angular.forEach(cities,function (e) {
            if(e.p=='北京'||e.p=='天津'||e.p=='上海'||e.p=='香港'||e.p=='澳门'||e.p=='台湾'){
                var obj = new Object();
                flag = false;
                obj.Province = e.p;
                obj.City = e.p;
                obj.IsHot = 0;
                arr.push(obj);
            }else if(e.p == '国外'){
                flag = false;
            }else{
                flag = true;
            }
            if(flag){
                angular.forEach(e.c,function (c) {
                    var obj1 = new Object()
                    obj1.Province = e.p;
                    obj1.City = c.n;
                    obj1.IsHot = 0;
                    arr.push(obj1);
                })
            }
        })
        var url = appConfig.url + 'HotCity/importCity';
        var method = 'POST';
        var data = arr;
        var promise = sc.httpDataUrl(url,method,data);
        promise.then(function (data) {
            sc.processResult(data);
        }),function (data) {
            sc.Load_Failed(data);
        }
    }

    sc.IsImprotCity = false;

    sc.getProvince = function () {
        var p = new Array();
        var url = appConfig.url + 'HotCity/getProvince';
        var method = 'GET';
        var params = {};
        var promise = sc.httpParams(url,method,params);
        promise.then(function (data) {
            p = data;
            if(p.length==0){
                sc.IsImprotCity = true;
            }else if(p.length>0){
                sc.IsImprotCity = false;
            }
        }),function (data) {
            sc.Load_Failed(data);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    sc.createPointLng = null;
    sc.createPointLat = null;
    sc.createPointAddress = null;

    //初始化地图
    var map = new AMap.Map('mapView',{
        resizeEnable: true,
        zoom: 9,
        center: [113.12,23.02],//new AMap.LngLat(116.39,39.9)
        scrollWheel : false,
    });
    map.plugin(['AMap.ToolBar'],function(){
        map.addControl(new AMap.ToolBar());
    });
    var marker = null;

    var Marker = function (lng,lat) {
        marker = new AMap.Marker({
            position: [lng,lat]
        });
        marker.setMap(map);
    }
    Marker(113.12,23.02);

    var createPointInfowindowContent = $('<div>\
                                            <div id="address"></div>\
                                            <div id="lng"></div>\
                                            <div id="lat"></div>\
                                            <div class="p-t-10">\
                                                <button id="readyPoint" class="btn btn-danger">确认标点</button>\
                                            </div>\
                                          </div>');

    var createPointInfowindow = new AMap.InfoWindow({
        content: createPointInfowindowContent[0],
        offset: new AMap.Pixel(0, 0),
        size:new AMap.Size(230,0)
    })

    createPointInfowindowContent.find('#readyPoint').on('click',function () {
        map.clearMap();
        Marker(sc.createPointLng,sc.createPointLat);

    })

    sc.MapSearch = function () {
        AMap.service(["AMap.PlaceSearch"], function() {
            var placeSearch = new AMap.PlaceSearch({ //构造地点查询类
                pageSize: 5,
                pageIndex: 1,
                map: map,
                panel: "panel"
            });
            //关键字查询
            placeSearch.search(sc.addressKey);
        });
    }




    map.on('click',function (e) {
        sc.createPointLng=e.lnglat.getLng();
        sc.createPointLat=e.lnglat.getLat();
        sc.createPointAddress=null;
        createPointInfowindow.open(map,new AMap.LngLat(sc.createPointLng,sc.createPointLat));
        createPointInfowindowContent.find('#address').text('地址:'+'正在查询,请稍等。。。');
        createPointInfowindowContent.find('#lng').text('经度:'+sc.createPointLng);
        createPointInfowindowContent.find('#lat').text('经度:'+sc.createPointLat);

        AMap.service(["AMap.Geocoder"], function() { //加载地理编码
            geocoder = new AMap.Geocoder({
                radius: 1000,
                extensions: "all"
            });
            // Marker(createPointLng,createPointLat);
            //步骤三：通过服务对应的方法回调服务返回结果，本例中通过逆地理编码方法getAddress回调结果
            geocoder.getAddress(new AMap.LngLat(sc.createPointLng,sc.createPointLat), function(status, result){
                //根据服务请求状态处理返回结果
                if(status=='error') {
                    alert("服务请求出错啦！ ");
                }
                if(status=='no_data') {
                    alert("无数据返回，请换个关键字试试～～");
                }
                else {
                    console.log(result)
                    sc.createPointAddress= result.regeocode.formattedAddress;

                    createPointInfowindowContent.find('#address').text('地址:'+sc.createPointAddress);
                }
            });
        });
    });

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


}