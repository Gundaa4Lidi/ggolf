var ngTime = function() {
    return {
        restrict : 'A',
        require : '?ngModel',
        // scope :{
        //     startDate :'=',
        //     endDate :'='
        // },
        //minView(可选精确的时间域)&&maxView(最高能展示的时间)
        //view: 0:分钟,1:小时,2:日期,3:月份,4:年份
        link : function(scope, $element, $attrs, $ngModel) {
            if (!$ngModel) {
                return;
            }

            $('.form_datetime').datetimepicker({
                language:  'zh-CN',
                weekStart: 0,
                todayBtn:  1,
                autoclose: 1,
                showMeridian: 1,
                todayHighlight: 1,
                startView: 2,
                forceParse: 0,
                minuteStep: 30,
                showMeridian: 1,
                pickerPosition: 'bottom-left',
            });
            $('.form_date').datetimepicker({
                language:  'zh-CN',
                weekStart: 0,
                todayBtn:  1,
                autoclose: 1,
                todayHighlight: 1,
                startView: 2,
                minView: 2,
                showMeridian: 1,
                forceParse: 0,
                pickerPosition: 'bottom-left',
            });
            $('.form_time').datetimepicker({
                language:  'zh-CN',
                weekStart: 0,
                todayBtn:  1,
                autoclose: 1,
                todayHighlight: 1,
                startView: 1,
                minView: 0,
                maxView: 1,
                showMeridian: 1,
                minuteStep: 10,
                forceParse: 0,
                pickerPosition: 'bottom-left',
            });

        },
    };
}