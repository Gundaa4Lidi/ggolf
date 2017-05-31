var pageLoader = function($window, $document, $compile) {
    return {
        restrict: 'A',
        scope:{
            isLoading:'=',
        },
        link: function(scope, ele, attrs){
            var elShare = angular.element( '<div id="page-loader" >\
                                                <div class="page-loader__spinner"></div>\
                                            </div>');
            function showShare() {
                if (!elShare.hasClass('ng-scope')) {
                    $compile(elShare)(scope);
                    var body = $document.find('body').eq(0);
                    var pageLoader = document.getElementById('page-loader');
                    if(!pageLoader){
                        body.append(elShare);
                    }else{
                        pageLoader.style.opacity = 1;
                        pageLoader.style.display = 'block';
                    }
                }
                elShare.show();
            }
            showShare();


            scope.$watch('isLoading',function (newValue) {
                if(newValue){
                    setTimeout(function () {
                        fade(document.getElementById('page-loader'));
                    }, 500);
                }
            })
            function fade(element) {
                var op = 1;  // initial opacity
                var timer = setInterval(function () {
                    if (op <= 0.1){
                        clearInterval(timer);
                        element.style.display = 'none';
                    }
                    element.style.opacity = op;
                    element.style.filter = 'alpha(opacity=' + op * 100 + ")";
                    op -= op * 0.1;
                }, 10);
            }

        }
    }
}