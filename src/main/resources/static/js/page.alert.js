/**
 * Created by jecyhw on 16-11-2.
 */
//页面提示信息
function pageAlert(msg, msgType) {
    var $page_alert = $('#page-alert');
    if ($page_alert.length == 0) {
        $page_alert = $('<div></div>');
        $page_alert.attr("id", "page-alert");
        $(document.body).append($page_alert);
        $page_alert.css({
            fontSize: '14px',
            position: 'fixed',
            left: '0px',
            bottom: '10px',
            zIndex: 10000
        });
    }
    var $div = $('<div></div>');
    $div.append(msgType ? ('<div class="page-alert"><div class="alert alert-' + msgType + '" role="alert" style="display: inline-block;">' + msg + '</div></div>') : msg);
    $div.find('.page-alert').each(function () {
        (function (it) {
            if ($page_alert.children().length > 5) {
                $page_alert.children().eq(0).remove();
            }
            it.appendTo($page_alert).hide().slideDown('slow', function () {
                var close_page_notice = function () {
                    it.slideUp('slow', function() {
                        it.remove();
                    });
                    clearInterval(notice_timer);
                };
                var notice_timer = setInterval(function () {
                    close_page_notice();
                }, 5000);
                it.find('button.close').click(function () {
                   close_page_notice();
                });
                it.mouseover(function () {
                    close_page_notice();
                });
            });
        })($(this));
    });
}
