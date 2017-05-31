var scrollTop = function ($window, $document, $compile) {
    return {
        restrict: 'A',
        link: function ($scope, elem, attr, ctrl) {
            $scope.side = attr.side;
            $scope.title = attr.title;
            var elShare = angular.element('<div class="side-tool">' +
                                            '<a href="#" ng-click="goTop()" ' +
                                                'tooltips tooltip-side="{{side}}" tooltip-template="{{title}}">' +
                                                '<i class="fa fa-chevron-up" aria-hidden="true"></i>' +
                                            '</a>' +
                                          '</div>')
            function showShare() {
                if (!elShare.hasClass('ng-scope')) {
                    $compile(elShare)($scope);
                    var body = $document.find('body').eq(0);
                    body.append(elShare);
                }
                elShare.show();
            }

            var loading = false;

            $scope.goTop = function () {
                var idToScroll = attr.href;

                var $target;
                if (idToScroll) {
                    $target = $(idToScroll);
                } else {
                    $target = elem;
                }

                loading = true;
                $("body").animate({ scrollTop: $target.offset().top }, "fast", function () {
                    elShare.hide();
                    loading = false;
                });
                // var scrollToTarget = 'html';
                // var scrollToOffset = 0;
                // $('html, body').animate({
                //     scrollTop: ($(scrollToTarget).offset().top) - scrollToOffset
                // }, 500);
            }

            var scrollTop = 0;
            var windowEl = angular.element($window);
            var handler = function () {
                if (loading) return;
                var ele = document.body;
                if (ele.scrollTop > 100) {
                    showShare();
                } else {
                    elShare.hide();
                }
                scrollTop = document.body.scrollTop;
            }
            windowEl.on('scroll', $scope.$apply.bind($scope, handler));
            handler();
        }
    };
}