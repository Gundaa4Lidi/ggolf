var ArticleController = function($scope,$rootScope,$http,$q,$window,Upload,appConfig,$timeout){
	var sc = $scope;
	var ArticlePageCtrl = sc.$parent;

    sc.Articles = new Object();
	sc.Categories = new Object();
	sc.articleTypes = new Object();
	sc.currentCategory = new Object();
	sc.currentType = new Object();
	sc.currentSubject = new Object();
	sc.currentArticle = new Object();
	sc.coverImage = new Object();
	sc.recycles = new Object();
	sc.TypeTitle = new Object();
//	sc.currrentType.TypeKey = "文章";
	sc.coverCroppedImage = new Object();
	sc.SubOrNot = '0';
	sc.open_add = false;
	sc.selects = [
      {type: "文章",id: "1"},
      {type: "图片",id: "2"},
      {type: "视频",id: "3"}
	]

    sc.searchClean = function () {
        sc.currentType = new Object();
        sc.currentSubject = new Object();
        sc.getSearch();
        sc.load();
    }

	sc.status = {
		indexC : 0,
		indexT : 0
	}

    sc.rows = 10;
    sc.Rows = 0;
    sc.loadMore = false;
    sc.TotalArticle = 0;

    sc.loading = function(){
        if(sc.loadMore) {
            sc.rows += 30;
            sc.getSearch();
        }
    }

    // sc.$watch('Rows',function(newValue){
    //     if(newValue < sc.TotalArticle){
    //         sc.loadMore = true;
    //     }else if(newValue >= sc.TotalArticle){
    //         sc.loadMore = false;
    //     }
    // })
	
	sc.selectSub = function(sub){
		if(sub=='0'){
			sc.Sub = "头条";
		}else if(sub=='1'){
			sc.Sub = "专题";
		}
		sc.SubOrNot = sub;
	}
	sc.selectKey = function(key){
		sc.currentType.TypeKey = key;
	}
	
	sc.load = function() {
        sc.getArticleTree();
        // sc.getSearch();
	
	}
    sc.addCategory = function(e){
        sc.currentCategory = new Object();
        sc.saveCategory(e);
        sc.Category = null;
    }
    sc.addType = function(){
        sc.currentType = new Object();
        var key = sc.selects[0].type;
        sc.selectKey(key);
    }

    sc.addSubject = function(){
        sc.currentSubject = new Object();
    }

    sc.addArticle = function(){
        sc.currentArticle = new Object();
        sc.currentArticle.Video = "";
        var type = sc.currentType;
        if(type.TypeKey == '文章'){
        	$('#new-note').modal('show');
		}
		if(type.TypeKey == '图片'){
            $('#new-photo').modal('show');
        }
        if(type.TypeKey == '视频'){
            $('#new-note').modal('show');
        }
    }



    sc.editArticle = function(e){
        sc.currentArticle = angular.copy(e);
        sc.setVideo(e.Video);
    }

    sc.check = function(e,index){
        sc.currentCategory = angular.copy(e);
        sc.status.indexC = index;
    }
    sc.check2 = function(e,indexC,indexT){
        sc.currentType = new Object();
        sc.currentSubject = new Object();
    	sc.status.indexC = indexC;
    	sc.status.indexT = indexT;
        if(sc.currentCategory.SubOrNot == '1'){//是专题的话,点击文集类别,不能新建文章
            sc.open_add = false;
            sc.TotalArticle = 0;
        }else{//不是专题的话,当前专题为null,用于新建文章时,不混淆专题
            sc.open_add = true;
        }
        sc.currentType = angular.copy(e);
        sc.getSearch();
        // sc.getArticleByTypeID();
    }


    sc.check3 = function(e){
        sc.currentArticle = angular.copy(e);
    }

    //跳转到文章内容
    sc.checkArticle = function(e){
        ArticlePageCtrl.changeArticleData(e);
        ArticlePageCtrl.changeArticlePage(2);
    }

    sc.checkSub = function(e){
    	sc.open_add = true;
        sc.currentSubject = angular.copy(e);
        sc.getSearch();
    }

    /**
	 * 刷新文集类别
     * @param CategoryID
     * @param index
     */
    sc.getTypeByCategoryID = function (CategoryID,index) {
		var url = appConfig.url + 'Article/getTypeByCategoryID';
        var method = 'GET';
        var params = {
            CategoryID : CategoryID,
        }
        var promise = sc.httpParams(url,method,params);
        promise.then(function (data) {
            if(data.status != "Error"){
				sc.Categories[index].articleType = data;
            }
        }),function (data) {
            sc.Load_Failed(data);
        }
    }

    /**
	 * 刷新专题
     * @param TypeID
     * @param indexC
     * @param indexT
     */
    sc.getSubjectByTypeID = function (TypeID,indexC,indexT) {
        var url = appConfig.url + 'Article/getSubjectByTypeID';
        var method = 'GET';
        var params = {
            TypeID : TypeID,
        }
        var promise = sc.httpParams(url,method,params);
        promise.then(function (data) {
            if(data.status != "Error"){
                sc.Categories[indexC].articleType[indexT].articleSubject = data;
            }
        }),function (data) {
            sc.Load_Failed(data);
        }
    }
    sc.IsRelease = [
        {key:"全部",id:"0"},
        {key:"已发布文章",id:"1"}
    ]

    sc.isRelease = sc.IsRelease[0].id;

    sc.relSelectOpt = {
        width : "130",
    }

    // sc.pageOptions = {
    //     currentPage : 1,
    //     itemsPerPage : 5,
    //     onChange:function () {
    //         sc.getSearch()
    //     }
    // }


    sc.getSearch = function () {
        var typeId = null;
        var subjectId = null;
        var at = sc.currentType;
        var as = sc.currentSubject;
        if(at.TypeID!=null){
            typeId = at.TypeID;
        }
        if(as.SubjectID!=null){
            subjectId = as.SubjectID;
            typeId = null;
        }
        var url = appConfig.url + 'Article/getSearch';
        var method = 'GET';
        var params = {
            IsRelease : sc.isRelease,
            TypeID : typeId,
            SubjectID : subjectId,
            keyword : sc.keyword,
            rows : sc.rows
        }
        var promise = sc.httpParams(url,method,params);
        promise.then(function (data) {
            sc.Articles = data.articles;
            sc.TotalArticle = data.count;
            sc.Rows = sc.rows;
            // sc.pageOptions.totalItems = data.count;
        }),function (data) {
            sc.Load_Failed(data);
        }
        sc.loadMore = sc.LoadMore(sc.Rows,sc.TotalArticle);
    }


	sc.getArticleByTypeID = function(){
		var at = sc.currentType
        var ReleaseOrAll = "";
        if(sc.isRelease=='0'){
            ReleaseOrAll = "all";
        }else if(sc.isRelease=='1'){
            ReleaseOrAll = "release";
        }
		var url = appConfig.url + 'Article/getArticleByTypeID';
		var method = 'GET';
		var params = {
			ReleaseOrAll : ReleaseOrAll,
			CategoryID : at.CategoryID,
			TypeID : at.TypeID
		}
		var promise = sc.httpParams(url,method,params);
		promise.then(function (data) {
            sc.Articles = data.articles;
            sc.TotalArticle = data.count;
        }),function (data) {
			sc.Load_Failed(data);
        }
	}
	
	sc.getArticleBySubjectID = function(){
		var as = sc.currentSubject;
        var ReleaseOrAll = "";
        if(sc.isRelease=='0'){
            ReleaseOrAll = "all";
        }else if(sc.isRelease=='1'){
            ReleaseOrAll = "release";
        }
        var url = appConfig.url + 'Article/getArticleBySubjectID';
        var method = 'GET';
        var params = {
            ReleaseOrAll:ReleaseOrAll,
            SubjectID:as.SubjectID
        }
        var promise = sc.httpParams(url,method,params);
        promise.then(function (data) {
            sc.Articles = data.articles;
            sc.TotalArticle = data.count;
        }),function (data) {
            sc.Load_Failed(data);
        }
	}


	
	sc.getArticleTree = function(){
        var url = appConfig.url + 'Article/getTree';
        var method = 'GET';
        var promise = sc.httpParams(url,method);
        promise.then(function(data){
            sc.Categories = data;
        }),(function(data){
            sc.Load_Failed(data);
        })
	}
	
	sc.saveType = function(){
		if(!sc.currentType.TypeName){
			swal("文集类别名不能为空","","warning");
		}else if(!sc.currentType.TypeKey){
			swal("请选择类别内容","","warning");
		}else{
			var cd = sc.currentCategory;
			sc.currentType.CategoryID = cd.CategoryID;
			var url = appConfig.url + 'Article/saveArticleType';
			var method = 'POST';
			var data = sc.currentType;
			var promise = sc.httpDataUrl(url,method,data);
			promise.then(function (data) {
				sc.processResult(data);
                if(data.status != 'Error'){
                    $("#edit_type").modal("hide");
                    sc.getTypeByCategoryID(cd.CategoryID,sc.status.indexC);//刷新文集类别
                }
            })
		}
	}
	
	sc.saveSub = function(){
		if(!sc.currentSubject.SubjectName) {
            swal("专题名不能为空", "", "warning");
        }else if(!sc.currentSubject.Attr) {
			swal("专题关键字不能为空,例如: 教学");
		}else{
            var td = sc.currentType;
            sc.currentSubject.TypeID = td.TypeID;
            sc.currentSubject.CategoryID = td.CategoryID;
			var url = appConfig.url + 'Article/saveArticleSubject';
			var method = 'POST';
            var data = sc.currentSubject;
            var promise = sc.httpDataUrl(url,method,data);
            promise.then(function(data){
            	sc.processResult(data);
            	if(data.status != 'Error'){
					$("#edit_subject").modal("hide");
                    sc.getSubjectByTypeID(td.TypeID,sc.status.indexC,sc.status.indexT);//刷新专题
                }
			}),function(data){
            	sc.Load_Failed(data);
			}
		}
	}
	
	sc.saveCategory = function(CategoryName){
		if(CategoryName){
			sc.currentCategory.CategoryName = angular.copy(CategoryName)
			sc.currentCategory.SubOrNot = sc.SubOrNot;
			var url = appConfig.url + 'Article/saveArticleCategory';
			var method = 'POST';
			var data = sc.currentCategory;
			var promise = sc.httpDataUrl(url,method,data);
			promise.then(function (data) {
                sc.processResult(data);
                if(data.status != 'Error'){
					$("#edit_Category").modal("hide");
                    sc.load();
                }
            })
		}else{
			swal("请输入文集名","","warning");
		}
	}
	
	sc.saveArticle = function(){
		if(!sc.currentArticle.Title){
			swal("请填写文章标题!","","warning");
		}else if(!sc.currentArticle.Cover){
			swal("请选择文章封面!","","warning");
		}else if(!sc.currentArticle.Content){
			swal("请填写文章简介!","","warning");
        }else if(sc.currentType.TypeKey=='视频'&&!sc.currentArticle.Video){
            swal("请上传视频!","","warning");
		}else{
            var td = sc.currentType;
            sc.currentArticle.TypeKey = td.TypeKey;
            if(sc.currentSubject==null){
                sc.currentArticle.CategoryID =td.CategoryID;
                sc.currentArticle.TypeID = td.TypeID;
                sc.currentArticle.TypeName = td.TypeName;
                var url = appConfig.url + 'Article/saveArticle';
                var method = 'POST';
                var data = sc.currentArticle;
                var promise = sc.httpDataUrl(url,method,data);
                promise.then(function(data){
                    sc.processResult(data);
                    if(data.status != 'Error'){
                        $("#new-note").modal("hide");
                        sc.getSearch();
                    }
				}),function(data){
                	sc.Load_Failed(data);
				}
            }else{
				var sub = sc.currentSubject;
                sc.currentArticle.CategoryID =sub.CategoryID;
                sc.currentArticle.TypeID = sub.TypeID;
                sc.currentArticle.TypeName = sub.Attr;
                sc.currentArticle.SubjectID = sub.SubjectID;
                sc.currentArticle.SubjectName = sub.SubjectName;
				var url = appConfig.url + 'Article/saveArticle';
				var method = 'POST';
				var data = sc.currentArticle;
				var promise = sc.httpDataUrl(url,method,data);
				promise.then(function(data){
					console.log(data)
					sc.processResult(data);
                    if(data.status != 'Error'){
                        $("#new-note").modal("hide");
                        sc.getSearch();
                    }
				}),function(data){
					sc.Load_Failed(data);
				}
			}
		}
	}
	
	sc.getRecycle_bin = function(){
        var url = appConfig.url + 'Article/getbyRemove';
        var method = 'GET';
        var promise = sc.httpParams(url,method);
        promise.then(function(data){
            if(data.status == "Error"){
                sc.TotalRecycle= 0;
                sc.recycles = new Object();
            }else{
                sc.recycles = data;
                sc.TotalRecycle = data.length;
            }
        }),function(data){
            sc.Load_Failed(data);
        }
	}
	//还原文章
	sc.restore = function(art){
		var id = art.ArticleID;
		var url = appConfig.url + 'Article/restoreArticle';
		var method = 'POST';
		var params = {
			ArticleID : id
		}
        var promise = sc.httpParams(url,method,params);
        promise.then(function (data) {
            sc.processResult(data);
            sc.getRecycle_bin();
            sc.getSearch();
            // if(art.SubjectID){
            //     sc.getArticleBySubjectID();
            // }else{
            //     sc.getArticleByTypeID();
            // }
        }),function (data) {
            sc.Load_Failed(data);
        }
	}
	//彻底删除文章
	sc.deleteArticle = function(id){
        swal({
            title : "确认清除此文章?",
            text : "清除后将无法恢复!",
            type : "warning",
            showCancelButton : true,
            confirmButtonColor : "#DD6B55",
            confirmButtonText : "是的",
            cancelButtonText : "取消",
            closeOnConfirm : false
        }, function() {
            var url = appConfig.url + 'Article/deleteArticle';
            var method = 'POST';
            var params = {
                ArticleID : id
            }
            var promise = sc.httpParams(url,method,params);
            promise.then(function (data) {
                sc.processResult(data);
                if(data.status != 'Error'){
                    sc.getRecycle_bin();
                }
            }),function (data) {
                sc.Load_Failed(data);
            }

        });
	}

	sc.removeArt = function(art){
		sc.currentArticle = art;
		sc.remove(art.CategoryID,art.TypeID,art.SubjectID,art.ArticleID);
	}

	sc.remove = function(CategoryID,TypeID,SubjectID,ArticleID){
		var removeObj = {};
		removeObj.CD = null;
		removeObj.TD = null;
		removeObj.SD = null;
		removeObj.AD = null;
		if(CategoryID&&TypeID&&ArticleID){
			removeObj.CD = CategoryID;
			removeObj.TD = TypeID;
			removeObj.AD = ArticleID;
			removeObj.Title = "移除项目\n"+sc.currentArticle.Title+"?";
			removeObj.Text = "移除到回收站 ";
		}else if(CategoryID&&TypeID&&SubjectID&&!ArticleID){
			removeObj.CD = CategoryID;
			removeObj.TD = TypeID;
			removeObj.SD = SubjectID;
			removeObj.Title = "移除专题\n"+sc.currentSubject.SubjectName+"?";
			removeObj.Text = "专题下的所有项目移除到回收站 ";
		}else if(CategoryID&&TypeID&&!SubjectID&&!ArticleID){
			removeObj.CD = CategoryID;
			removeObj.TD = TypeID;
			removeObj.Title = "移除文集类别\n"+sc.currentType.TypeName+"?";
			removeObj.Text = "文集类别下的所有项目将移除到回收站";
		}else if(CategoryID&&!TypeID&&!SubjectID&&!ArticleID){
			removeObj.CD = CategoryID;
			removeObj.Title = "移除文集\n"+sc.currentCategory.CategoryName+"?";
			removeObj.Text = "文集下的所有项目将移除到回收站";
		}
		sc.removeAlert(removeObj)
		
	}

    // sc.selectImg = function (index) {
    //     $timeout.cancel(sc.timeout);
    //     sc.timeout = $timeout(function () {
    //         sc.active = index;
    //     },200);
    //
    // }
    //
    // sc.deleteImg = function(index){
    //     sc.PhotoList.splice(index,1);
    // }
    //
    // sc.callbackFile = "";
	
	sc.upload = function (files) {
        if (files && files.length) {
            for (var i = 0; i < files.length; i++) {
                var file = files[i];
                console.log(file)
                Upload.upload({
                    url: '/GGolfz/rest/file/upload',
                    file: file
                }).progress(function (evt) {
                    var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
                    console.log('progress: ' + progressPercentage + '% ' + evt.config.file.name);
                }).success(function (data) {
                	sc.currentArticle.Cover = data;
					// sc.callbackFile = data;
                }).error(function (data) {
                    swal("上传失败",data,"error");
                })
            }
        }
    };

	sc.videoSize = 0;
	sc.videoProgress = 0;
    sc.barTypes = ['success', 'info', 'warning', 'danger'];
	sc.uploadVideo = function (files) {
        if (files && files.length) {
            for (var i = 0; i < files.length; i++) {
                var file = files[i];
                var fileExt = file.name.substring(file.name.lastIndexOf(".")+1).toLowerCase();//获得文件后缀名
                console.log(fileExt)
                //rm,rmvb,avi,mp4,3gp
                if(fileExt!='rm' && fileExt!='rmvb' && fileExt!='avi' && fileExt!='mp4' && fileExt!='3gp'){
                    swal("请选择视频格式文件上传(rm,rmvb,avi,mp4,3gp等)","","warning");
                }else{
                    Upload.upload({
                        url: '/GGolfz/rest/file/upload',
                        file: file
                    }).progress(function (evt) {
                        sc.videoSize = parseFloat(evt.total / (1024 * 1024)).toFixed(2);
                        sc.videoProgress = parseFloat(evt.loaded / (1024 * 1024)).toFixed(2);
                        if(sc.videoProgress < 100){
                            sc.barType = sc.barTypes[1];
                        }
                        var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
                        console.log('progress: ' + progressPercentage + '% ' + evt.config.file.name);
                    }).success(function (data) {
                        sc.barType = sc.barTypes[0];
                        sc.currentArticle.Video = data;
                        sc.setVideo(data);
                    }).error(function (data) {
                        sc.barType = sc.barTypes[3];
                        swal("上传失败",data,"error");
                    })

                }
            }
        }
    }
    
    sc.removeAlert = function(removeObj){
		swal({
			title : ""+removeObj.Title+"",
			text : ""+removeObj.Text+"",
			type : "warning",
			showCancelButton : true,
			confirmButtonColor : "#DD6B55",
			confirmButtonText : "是的",
			cancelButtonText : "取消",
			closeOnConfirm : false
		}, function() {
            var url = appConfig.url + 'Article/remove';
            var method = 'POST';
            var params = {
                CategoryID:removeObj.CD,
                TypeID:removeObj.TD,
                SubjectID:removeObj.SD,
                ArticleID:removeObj.AD,
            }
            var promise = sc.httpParams(url,method,params);
            promise.then(function (data) {
                sc.processResult(data);
                if(removeObj.CD,removeObj.TD,removeObj.AD){
                    sc.getSearch();
                }else{
                    sc.load();
                }
            }),function (data) {
                sc.Load_Failed(data);
            }

		});
	}
    
    // var handleFileSelect = function (evt) {
	 //        var file = evt.currentTarget.files[0];
	 //        var reader = new FileReader();
	 //        reader.onload = function (evt) {
	 //        	sc.$apply(function ($scope) {
	 //        		sc.coverImage = evt.target.result;
	 //            });
	 //        };
	 //        reader.readAsDataURL(file);
    // };
    // angular.element(document.querySelector('#fileInput')).on('change', handleFileSelect);



    sc.videoTemplate =
        '<div>' +
            '<span class="fr-video fr-fvc fr-draggable fr-dvb fr-active"' +
                ' contenteditable="false" draggable="true">' +
                '<iframe height="240" width="360"' +
                    ' src="http://player.youku.com/embed/XMjc1MTU1NDE5Ng==" frameborder="0">' +
                '</iframe>' +
            '</span>' +
        '</div>';

    sc.myVideo = null;

    sc.setVideo = function (video) {
        sc.myVideo =
            '<div>' +
            '   <span class="fr-video fr-draggable fr-dvb" contenteditable="false" draggable="true">' +
            '       <video class="fr-draggable fr-fic fr-dii" controls="" height="240" src="'+video+'"' +
            '        style="background: black;"width="360">' +
            '       </video>' +
            '   </span>' +
            '</div>';
    }

}