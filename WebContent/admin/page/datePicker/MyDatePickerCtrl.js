var MyDatePickerController = function ($scope,appConfig) {
    var sc = $scope;

    sc.today = function(){
        sc.DateTime = new Date();
    }
    sc.today();

    sc.options = {
        minMode: 'day',
        maxMode: 'year',
        showWeeks: true,
        startingDay: 0,
        yearRange: 20,
        minDate: null,
        maxDate: null,
        shortcutPropagation: false
    };
}