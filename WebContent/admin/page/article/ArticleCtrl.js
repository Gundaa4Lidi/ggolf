var ArticleController = function($scope,$rootScope,$http,$q,$window,Upload,appConfig,$timeout){
	var sc = $scope;
	var ArticlePageCtrl = sc.$parent;

    sc.Articles = null;
	sc.Categories = null;
	sc.articleTypes = null;
	sc.currentCategory = null;
	sc.currentType = null;
	sc.currentSubject = null;
	sc.currentArticle = null;
	sc.coverImage = null;
	sc.recycles = null;
	sc.TypeTitle = null;
//	sc.currrentType.TypeKey = "文章";
	sc.coverCroppedImage = null;
	sc.TotalArticle = 0;
	sc.SubOrNot = '0';
	sc.open_add = false;
	sc.selects = [
      {type: "文章",id: "1"},
      {type: "图片",id: "2"},
      {type: "视频",id: "3"}
	]
	
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
	
    // sc.config = {
    //     height: '300px',
    //     filterMode: false,
    //     items: ['source', '|', 'undo', 'redo', '|', 'preview', '|', 'justifyleft', 'justifycenter', 'justifyright', 'justifyfull', '|', 'insertorderedlist', 'insertunorderedlist', '|',
    //     'fontname', 'fontsize', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline', '|', 'table', '|', 'image']
    // }
	

	sc.load = function() {
		var promise = sc.getArticle();
		promise.then(function(data){
			/*console.log(data)*/
			sc.Categories = data;
		}),(function(data){
            sc.Load_Failed(data);
		})
	
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
        sc.test();
    }
    sc.test = function(){
    	var url = appConfig.url + 'Score/create';
    	var method = 'POST';
    	var params = {
			standardScores : '1',
			score : '1',
			userId:'2',
			playGroundName:'dfdf',
			playerName:'fdf'
    	}
    	var promise = sc.httpParams(url,method,params);
    	promise.then(function (data) {
    		console.log(data);
            sc.processResult(data);
        }),function(data){
    		sc.Load_Failed(data);
    	}
    }
    

    sc.editArticle = function(e){
        sc.currentArticle = e;
    }

    sc.check = function(e){
        sc.currentCategory = e;
    }
    sc.check2 = function(e){
        if(sc.currentCategory.SubOrNot == '1'){//是专题的话,点击文集类别,不能新建文章
            sc.open_add = false;
            sc.TotalArticle = 0;
            sc.currentType = new Object();
        }else{//不是专题的话,当前专题为null,用于新建文章时,不混淆专题
            sc.open_add = true;
            sc.currentSubject = null;
        }
        sc.TypeTitle = e.TypeName
        sc.currentType = new Object();
        sc.currentType = angular.copy(e);
		sc.getArticleByTypeID();
    }

    sc.check3 = function(e){
        sc.currentArticle = e;
    }

    //跳转到文章内容
    sc.checkArticle = function(e){
        ArticlePageCtrl.changeArticleData(e);
        ArticlePageCtrl.changeArticlePage(2);
    }

    sc.checkSub = function(e){
    	sc.open_add = true;
        sc.currentSubject = e;
        sc.getArticleBySubjectID();
    }


	sc.getArticleByTypeID = function(){
		var at = sc.currentType
		var ReleaseOrAll = "all";
		var url = appConfig.url + 'Article/getArticleByTypeID';
		var method = 'GET';
		var params = {
			ReleaseOrAll : ReleaseOrAll,
			CategoryID : at.CategoryID,
			TypeID : at.TypeID
		}
		var promise = sc.httpParams(url,method,params);
		promise.then(function (data) {
            if(data.status == "Error"){
                sc.TotalArticle = 0;
                sc.Articles = new Object();
            }else{
                sc.Articles = data;
                sc.TotalArticle = data.length;
            }
        }),function (data) {
			sc.Load_Failed(data);
        }
	}
	
	sc.getArticleBySubjectID = function(){
		var as = sc.currentSubject;
		var ReleaseOrAll = "all";
		$http({
			url: '/GGolfz/rest/Article/getArticleBySubjectID',
			method: 'GET',
			params:{
				ReleaseOrAll:ReleaseOrAll,
				SubjectID:as.SubjectID
			}
		}).then(function(response){
			/*console.log(response.data)*/
			var data = response.data;
			if(data.status == "Error"){
				sc.TotalArticle = 0;
                sc.Articles = new Object();
			}else{
				sc.Articles = data;
				sc.TotalArticle = data.length;
			}
		},function(response){
			sc.errorResult(response);
		})
	}
	
	sc.getArticle = function(){
		var ReleaseOrAll = "all";
		var dfd = $q.defer();
		$http({
			url: '/GGolfz/rest/Article/getArticle?&ReleaseOrAll='+ReleaseOrAll,
			method: 'GET'
		}).then(
			function(response){
				console.log(response.data)
				dfd.resolve(response.data);
			},
			function(response) {
				sc.errorResult(response)
				dfd.reject(response)
			}
		)
		return dfd.promise; 
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
				sc.load()
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
            	sc.load();
			}),function(data){
            	sc.Load_Failed(data);
			}
		}
	}
	
	sc.saveCategory = function(CategoryName){
		if(CategoryName){
			sc.currentCategory.CategoryName = CategoryName
			sc.currentCategory.SubOrNot = sc.SubOrNot;
			var url = appConfig.url + 'Article/saveArticleCategory';
			var method = 'POST';
			var data = sc.currentCategory;
			var promise = sc.httpDataUrl(url,method,data);
			promise.then(function (data) {
                sc.processResult(data);
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
		}else{
			if(sc.currentSubject==null){
                var td = sc.currentType;
                sc.currentArticle.CategoryID =td.CategoryID;
                sc.currentArticle.TypeID = td.TypeID;
                sc.currentArticle.TypeName = td.TypeName;
                var url = appConfig.url + 'Article/saveArticle';
                var method = 'POST';
                var data = sc.currentArticle;
                var promise = sc.httpDataUrl(url,method,data);
                promise.then(function(data){
                    $("#new-note").modal("hide");
                    sc.processResult(data);
                    sc.getArticleByTypeID();
				}),function(data){
                	sc.Load_Failed(data);
				}
            }else{
				var sub = sc.currentSubject;
                sc.currentArticle.CategoryID =sub.CategoryID;
                sc.currentArticle.TypeID = sub.TypeID;
                sc.currentArticle.TypeName = sub.Attr;
                sc.currentArticle.SubjectID = sub.SubjectID;
				var url = appConfig.url + 'Article/saveArticle';
				var method = 'POST';
				var data = sc.currentArticle;
				var promise = sc.httpDataUrl(url,method,data);
				promise.then(function(data){
					console.log(data)
                    $("#new-note").modal("hide");
					sc.processResult(data);
					sc.getArticleBySubjectID();
				}),function(data){
					sc.Load_Failed(data);
				}
			}
		}
	}
	
	sc.getRecycle_bin = function(){
		var dfd = $q.defer();
		$http({
			url: '/GGolfz/rest/Article/getbyRemove',
			method: 'GET'
		}).then(function(response){
			dfd.resolve(response.data);
			/*console.log(response)*/
			var data = response.data;
            if(data.status == "Error"){
                sc.TotalRecycle= 0;
                sc.recycles = new Object();
            }else{
                sc.recycles = data;
                sc.TotalRecycle = data.length;
            }
		},function(response){
			sc.errorResult(response)
			dfd.reject(response)
		})
		return dfd.promise;
	}
	//还原文章
	sc.restore = function(art){
		var id = art.ArticleID;
		var dfd = $q.defer();
		$http({
			url: '/GGolfz/rest/Article/restoreArticle?ArticleID='+id,
			method: 'POST'
		}).then(function(response){
			dfd.resolve(response.data);
			sc.processResult(response.data);
			sc.getRecycle_bin();
			if(art.SubjectID){
                sc.getArticleBySubjectID();
            }else{
                sc.getArticleByTypeID();
            }
		},function(response){
			sc.errorResult(response)
			dfd.reject(response)
		})
		return dfd.promise;
	}
	//彻底删除文章
	sc.deleteArticle = function(id){
		var dfd = $q.defer();
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
            $http({
                url: '/GGolfz/rest/Article/deleteArticle?ArticleID='+id,
                method: 'POST'
            }).then(function(response){
                dfd.resolve(response.data);
                sc.processResult(response.data);
                sc.getRecycle_bin();
            },function(response){
                sc.errorResult(response)
                dfd.reject(response)
            })
            return dfd.promise;

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
                }).success(function (data, status, headers, config) {
                	sc.currentArticle.Cover = data;
                }).error(function (data, status, headers, config) {
                	// console.log(data,status,headers,config)
                	alert('上传失败');
                    // console.log('error status: ' + status);
                })
            }
        }
    };
    
    sc.removeAlert = function(removeObj){
    	var dfd = $q.defer();
    	/*console.log(removeObj)*/
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
			$http({
				url:'/GGolfz/rest/Article/remove',
				method:'POST',
				params:{
					CategoryID:removeObj.CD,
					TypeID:removeObj.TD,
					SubjectID:removeObj.SD,
					ArticleID:removeObj.AD,
				}
			}).then(function(response){
				dfd.resolve(response.data);
				sc.processResult(response.data);
				if(removeObj.CD,removeObj.TD,removeObj.AD){
					sc.getArticleByTypeID();
				}else{
					sc.load();
				}
			},function(response){
				sc.errorResult(response)
				dfd.reject(response)
			})
		});
	}
    
	var handleFileSelect = function (evt) {
	        var file = evt.currentTarget.files[0];
	        var reader = new FileReader();
	        reader.onload = function (evt) {
	        	sc.$apply(function ($scope) {
	        		sc.coverImage = evt.target.result;
	            });
	        };
	        reader.readAsDataURL(file);
    };
    angular.element(document.querySelector('#fileInput')).on('change', handleFileSelect);
	
}