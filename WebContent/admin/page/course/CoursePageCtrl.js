var CoursePageController = function($scope){
    var sc = $scope;
    sc.courseData = new Object();

    sc.init =function(){
        sc.changeCoursePage(1);
    }

    sc.changeCoursePage = function(page){
        if(page == 1){
            sc.coursePage = 'page/course/course.html';
            sc.courseData = new Object();
        }else if(page == 2){
            sc.coursePage = 'page/course/courseDetail.html';
        }
    }

    sc.changeCourseData = function(e){
        sc.courseData = e;
    }
}