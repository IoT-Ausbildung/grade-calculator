$(document).ready(function() {
    console.log(location.pathname);
    $('div.active').removeClass('active').removeAttr('aria-current');
    $('a[href="' + location.pathname + '"]').closest('div').addClass('active').attr('aria-current', 'page');
});