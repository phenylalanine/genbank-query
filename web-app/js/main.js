/**
 * Created with IntelliJ IDEA.
 * User: monleezy
 * Date: 7/17/13
 * Time: 10:22 PM
 * To change this template use File | Settings | File Templates.
 */

(function($) {
    // Show modal by default if no query string
    var qmarkIndex = window.location.href.indexOf('?');
    console.log('test');
    if (qmarkIndex < 0 || qmarkIndex == window.location.href.length - 1) {
        $('#upload').modal('show');
    }
    // show upload modal on click
    $('#upload-nav').click(function(event) {
        $('#upload').modal('show');
    });
})(jQuery);