var ArticlePageController = function($scope){
    var sc = $scope;
    $scope.ArticleData = new Object();
    $scope.ArticleContent = new Object();
    $scope.init = function(){
        $scope.changeArticlePage(1);
    }

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