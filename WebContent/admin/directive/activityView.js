var activityView = function () {
    return {
        restrict:'A',
        scope:{
            isActive:'=',
            showSuccess:'=',
            noRelative:'=',
            delayTime:'@'
        },
        link:function (scope,element,attr) {
            var e=$(element);
            if (!scope.noRelative) {
                e.css('position','relative');
            }
            var $activityView=$('<div class="cover" style="display:none;'+attr.bgColor+'">\
                      <div class="spinner" >\
                      <span style="position:absolute;top:25%;left:50%;margin:-20px 0 0 -20px;" class="txt-color-greenDark"><big><big><big><big><big><big><i class=" fa fa-check"></i></big></big></big></big></big></big></span>\
                        <div class="spinner-container container1">\
                          <div class="circle1"></div>\
                          <div class="circle2"></div>\
                          <div class="circle3"></div>\
                          <div class="circle4"></div>\
                        </div>\
                        <div class="spinner-container container2">\
                          <div class="circle1"></div>\
                          <div class="circle2"></div>\
                          <div class="circle3"></div>\
                          <div class="circle4"></div>\
                        </div>\
                        <div class="spinner-container container3">\
                          <div class="circle1"></div>\
                          <div class="circle2"></div>\
                          <div class="circle3"></div>\
                          <div class="circle4"></div>\
                        </div>\
                      </div>\
                    </div>')
            var $success=$activityView.find('span');
            $success.append(scope.success);
            var $loading=$activityView.find('.spinner-container')
            e.append($activityView)
            var showActivityView =function (isdiaplay) {
                if (isdiaplay==true) {
                    if (scope.delayTime) {
                        setTimeout(function () {
                            if(!scope.isActive){
                                return;
                            }
                            $activityView.css('display','block');
                            $loading.css('display','block');
                            $success.css('display','none');
                        },scope.delayTime)
                    }
                    else{
                        $activityView.css('display','block');
                        $loading.css('display','block');
                        $success.css('display','none');
                    }
                }
                else{
                    $success.css('display','block');
                    $loading.css('display','none');
                    if (scope.showSuccess==true) {
                        setTimeout(function (){
                            $activityView.css('display','none');
                        }, 500);
                    }
                    else{
                        $activityView.css('display','none');
                    }
                }
            }
            // showActivityView(scope.)
            scope.$watch('isActive',function (newValue) {
                showActivityView(newValue);
            })

        }
    }
}