var errSrc = function(){
    return {
        restrict: 'A',
        link: function(scope, ele, attrs){
            scope.$watch(function(){
                return attrs['ngSrc'];
            },function(value){
                if(!value){
                    ele.attr('src',attrs.errSrc);
                }
            })
            ele.bind('error',function(){
                /*if(attrs.src!=attrs.errSrc){
                 attrs.$set('src',attrs.errSrc);
                 }*/
                ele.attr('src',attrs.errSrc);
            });
        }
    }
}