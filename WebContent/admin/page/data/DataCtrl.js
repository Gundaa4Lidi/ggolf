var DataController = function($scope,$http,$window,Upload){
	var sc = $scope;
	sc.Datas = null;
	sc.HeadPhoto = null;
	sc.Image = '';
    sc.CroppedImage = '';
	sc.PhotoTitle = "默认头像";
	sc.load = function(){
		$http.get("/GGolfz/rest/Config/getConfig/HeadPhoto").then(function(response){
			console.log("全部数据",response.data);
			sc.HeadPhoto = response.data.VALUE;
		})
	}
	
	
    
    sc.imgSave = function(e){
    	var obj = {};
    	obj.KEY = "HeadPhoto";
    	obj.Describe = sc.PhotoTitle;
    	obj.VALUE = e;
    	/*$http.post("/GGolfz/rest/Config",data).then(function (data){
    		console.log(data)
    		sc.processResult(data)
    		sc.load();
    	})*/
    	console.log(obj)
    	$http.post('/GGolfz/rest/Config', obj).then(function(response) {
			sc.processResult(response.data);
			sc.load();
		});
    }
    


    var handleFileSelect = function (evt) {
        var file = evt.currentTarget.files[0];
        var reader = new FileReader();
        reader.onload = function (evt) {
        	sc.$apply(function (sc) {
        		sc.Image = evt.target.result;
        		console.log("类型："+typeof sc.Image,"值:"+sc.Image)
            });
        };
        reader.readAsDataURL(file);
    };
    angular.element(document.querySelector('#fileInput')).on('change', handleFileSelect);

    // sc.processResult = function(data) {
	// 	console.log(data)
	// 	if (data.status == 'Success') {
	// 		swal({
	// 			title : "操作成功",
	// 			type : "success",
	// 		})
	// 	} else {
	// 		swal({
	// 			title : "操作失败",
	// 			type : "error",
	// 		})
	// 	}
	// }
	
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
                }).success(function (data, status, headers, config) {
                	sc.currentStaff.Head = data;
                    console.log('file ' + config.file.name + 'uploaded. Response: ' + data);
                }).error(function (data, status, headers, config) {
                	console.log(data,status,headers,config)
                	alert('上传失败');
                    console.log('error status: ' + status);
                })
            }
        }
    };
}