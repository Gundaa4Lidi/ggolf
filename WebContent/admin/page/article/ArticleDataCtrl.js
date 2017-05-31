var ArticleDataController = function($scope,appConfig,Upload){
    var sc = $scope;
    var ArticlePageCtrl = sc.$parent;
    sc.editor = false;
    sc.ArticleData = angular.copy(ArticlePageCtrl.ArticleData);
    // console.log(sc.ArticleData);
    sc.load = function () {
        if(sc.ArticleData.TypeKey == '视频'){
            videoContent();
        } else {
            var url = appConfig.url + 'Article/getArticleContent';
            var method = 'GET';
            var params = {
                ArticleID : sc.ArticleData.ArticleID
            }
            var promise = sc.httpParams(url,method,params);
            promise.then(function (data) {
                if(data){
                    if(data.Content!=""){
                        sc.articleHtml = data.Content;
                    }
                }else{
                    sc.articleHtml =
                        '<div>' +
                        '   <h1 style="text-align: center;">' +
                        '       <strong><span style="color: rgb(0, 0, 0);">'+sc.ArticleData.Title+'</span>' +
                        '       </strong>' +
                        '   </h1>' +
                        '   <p> <br> </p> ' +
                        '   <p><img src="'+sc.ArticleData.Cover+'" style="min-width: 500px;" class="fr-fic fr-dib" alt=""></p>' +
                        '   <p style="text-align: center;">'+sc.ArticleData.Content+'</p>'+
                        '</div>';
                }
            }),function(data){
                sc.Load_Failed(data);
            }

        }
    }

    sc.editData = function(){
        sc.editor = !sc.editor;
    }

    var videoContent = function () {
        sc.articleHtml =
            '<div>' +
            '   <h1 style="text-align: center;">' +
            '       <strong><span style="color: rgb(0, 0, 0);">'+sc.ArticleData.Title+'</span>' +
            '       </strong>' +
            '   </h1>' +
            '   <span class="fr-video fr-draggable fr-dvb" contenteditable="false" draggable="true">' +
            '       <video class="fr-draggable fr-fic fr-dii" controls="" height="480" src="'+sc.ArticleData.Video+'"' +
            '        style="background: black;"width="640">' +
            '       </video>' +
            '   </span>' +
            '   <p style="text-align: center;">'+sc.ArticleData.Content+'</p>'+
            '</div>';
    }

    /**
     * 发布文章
     */
    sc.releaseArticle = function(){
        if(!sc.ArticleData.Title){
            swal("请输入文章标题","","warning");
        }else if(!sc.ArticleData.Cover){
            swal("请选择文章封面","","warning");
        }else if(!sc.ArticleData.RootIN) {
            swal("请输入文章来源", "", "warning");
        }else if(!sc.ArticleData.Content){
            swal("请输入文章的简介","","warning");
        }else{
            sc.ArticleData.ReleaseOrNot = 'Y';
            var url = appConfig.url + 'Article/saveArticle';
            var method = 'POST';
            var data = sc.ArticleData;
            var promise = sc.httpDataUrl(url,method,data);
            promise.then(function(data){
                sc.processResult(data);
                sc.editor = false;
                $("#root_in").modal("hide");
            }),function(data){
                sc.Load_Failed(data);
            }
        }
    }

    sc.saveContent = function(){
        if(sc.ArticleData.TypeKey == '视频'){
            videoContent();
        }
        var url = appConfig.url + "Article/saveArticleContent";
        var method =  "POST";
        var data = {
            ArticleID : sc.ArticleData.ArticleID,
            Content : sc.articleHtml
        }
        // console.log(sc.articleHtml);
        var promise = sc.httpDataUrl(url,method,data);
        promise.then(function (data) {
            // sc.processResult(data);
            // sc.load()
        }),function(data){
            sc.Load_Failed(data);
        }
    }


    sc.back = function () {
        ArticlePageCtrl.changeArticlePage(1);
    }

    sc.$watch('articleHtml',function(data){
        console.log(data);
    })

    sc.froalaOptions = {
        language: "zh_cn", //配置语言
        zIndex: 2,
        height:'600px',

        toolbarButtons: ['fullscreen', '|', 'bold', 'italic', 'underline', 'strikeThrough','subscript',
            'superscript', 'paragraphFormat', 'fontFamily', 'fontSize', '|', 'emoticons', 'color', 'paragraphStyle', 'inlineStyle', '|',
            'align', 'formatOL', 'formatUL', 'indent', 'outdent', '-', 'insertImage', 'insertLink', 'insertFile', 'insertVideo',
            'insertTable', 'undo', 'redo', 'save', 'html'],
        //图片上传参数
        imageMove:false,
        imageUploadMethod: 'POST',
        imageUploadParam: 'file_name',
        imageUploadURL: appConfig.url + 'file/uploadFile',
        imageMaxSize: 5* 1024 * 1024,
        imageAllowedTypes: ['jpeg', 'jpg', 'png', 'gif','swt'],
        //文件上传参数
        fileUploadParam: 'file',
        fileUploadURL: appConfig.url + 'file/uploadFile',
        fileUploadMethod: 'POST',
        fileMaxSize: 20 * 1024 * 1024,
        fileAllowedTypes: ['*'],
        videoUploadMethod: 'POST',
        videoUploadParam: 'file',
        videoUploadURL: appConfig.url + 'file/uploadFile',
        videoDefaultDisplay: 'inline',
        videoDefaultAlign: 'center',
        videoAllowedTypes: ['mp4','avi','swt','flv'],
        videoDefaultWidth: 200,
        videoEditButtons: ['videoDisplay', 'videoRemove'],
        videoMaxSize: 1024 * 1024 * 15,
        videoResize: false,
        imageManagerLoadMethod:"GET",
        imageManagerLoadURL: appConfig.url + 'file/load_images',
        imageManagerDeleteMethod:"POST",
        imageManagerDeleteURL: appConfig.url + 'file/delete_image',
        saveInterval: 2500,
        saveMethod : 'POST',
        saveURL : appConfig.url + 'file/kong',//空接口
        events : {
            'froalaEditor.imageManager.imageDeleted': function (e, editor, data) {
                sc.processResult(data);
            },
            'froalaEditor.save.before': function () {
                sc.saveContent();
            },
            // 'froalaEditor.html.get': function (e, editor, html) {
            //     sc.saveContent(html);
            // }
        }

    }


    sc.deleteImage = function(e){
        var url = appConfig.url + "file/deleteImage";
        var method = "POST";
        var params = {
            filename : e[0].alt
        }
        var promise = sc.httpParams(url,method,params);
        promise.then(function(data){
            sc.processResult(data);
        }),function(data){
            sc.Load_Failed(data);
        }
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
                    sc.ArticleData.Cover = data;
                }).error(function (data, status, headers, config) {
                    // console.log(data,status,headers,config)
                    alert('上传失败');
                    // console.log('error status: ' + status);
                })
            }
        }
    };
}