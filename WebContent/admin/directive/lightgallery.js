var lightgallery = function(){
    return {
        restrict: 'A',
        link: function(scope, ele, attrs){
            if(scope.$last){
                ele.parent().lightGallery();
            }
            // if($(ele)[0]){
            //     $(ele).lightGallery();
            // }
        }
    }
}