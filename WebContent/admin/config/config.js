var config = function (tooltipsConfProvider,$httpProvider) {
    var token;
    if (window.sessionStorage.token&&window.sessionStorage.token.length>0) {
        token=window.sessionStorage.token
    }
    $httpProvider.defaults.headers.common['auth'] = token;
    tooltipsConfProvider.configure({
        'size': 'large',
        'speed': 'fast'
    });
}