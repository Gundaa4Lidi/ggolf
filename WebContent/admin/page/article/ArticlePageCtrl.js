var ArticlePageController = function($scope){
    var sc = $scope;
    $scope.ArticleData = new Object();
    $scope.ArticleContent = new Object();
    sc.currentCategory = new Object();
    sc.currentType = new Object();
    sc.currentSubject = new Object();
    sc.currentArticle = new Object();
    sc.keyword = '';
    sc.rows = 10;
    sc.IsRelease = [
        {key:"全部",id:"0"},
        {key:"已发布文章",id:"1"}
    ]

    sc.isRelease = sc.IsRelease[0].id;
    $scope.init = function(){
        $scope.changeArticlePage(1);
    }

    sc.$on('ArtConfig',function (event,data) {
        sc.keyword = data.keyword;
        sc.rows = data.rows;
        sc.isRelease = data.isRelease;
        sc.currentCategory = data.currentCategory;
        sc.currentType = data.currentType;
        sc.currentSubject = data.currentSubject;
        sc.currentArticle = data.currentArticle;
    })

    $scope.changeArticlePage = function(page){
        if(page == 1){
            $scope.articlePage = 'page/article/article.html';
            $scope.ArticleData = new Object();
            $scope.ArticleContent = new Object();
        }
        if(page == 2){
            $scope.articlePage = "page/article/articleData.html";
        }

    }

    sc.changeArticleData = function(data){
        sc.ArticleData = data
    }

}