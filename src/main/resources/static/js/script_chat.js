// 此為回到最上層按鈕的JS
{

    function deleteComment() {

    }
    $(document).ready(function () {
        $('html').click(function (e) {
            $('.chat-manager-list').css("display","none");
        });
        //edit_page = document.getElementById("chat_manager")
        $('.edit_button').click(function (e) {
            console.log(e.target.id);
            let elm = $(this);
            // var xPos = e.pageX - elm.offset().left;
            // var yPos = e.pageY - elm.offset().top;
            let xPos = e.pageX;
            let yPos = e.pageY;
            console.log(xPos, yPos);
            let count = setInterval(function() {
                $('.chat-manager-list').css("left", elm.offset().left - 100);
                $('.chat-manager-list').css("top", elm.offset().top + 35);
                $('.chat-manager-list').css("display", "");
                clearTimeout(count);
            }, 10);
        });
    });
}