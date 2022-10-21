{




    let click_button = document.querySelector('.click-to-revise');
    let check_button = document.querySelector('.check-to-revise');
    let authority = document.querySelectorAll('.authority');
    click_button.onclick = function (){
        for (let i = 0; i < authority.length; i++) {
            authority[i].classList.add('authority-enable');
            authority[i].classList.remove('authority');
        }
        click_button.style.display = "none";
        check_button.style.display = "";
        setEvent();
    };
    check_button.onclick = function (){
        for (let i = 0; i < authority.length; i++) {
            authority[i].classList.remove('authority-enable');
            authority[i].classList.add('authority');
        }
        click_button.style.display = "";
        check_button.style.display = "none";
        removeEvent();
    };
    function setEvent() {
        let authority_enable = document.querySelectorAll('.authority-enable');
        for (let i = 0; i < authority_enable.length; i++) {
            authority_enable[i].onclick = function (){
                authority_enable[i].classList.toggle('active');
            };
        }
    }
    function removeEvent() {
        for (let i = 0; i < authority.length; i++) {
            authority[i].onclick = "";
        }
    }
    //jquery
    $(document).ready(function () {
        $(".white_background").click(function (){
            $(".collaborator-manager-list").css("display", "none");
            $(".collaborator-calendar-list").css("display", "none");
            $(".white_background").css("display", "none");
        });
        //edit_page = document.getElementById("chat_manager")
        $('.edit_button_collaborator').click(function (e) {
            console.log(e.target.id);
            let elm = $(this);
            // var xPos = e.pageX - elm.offset().left;
            // var yPos = e.pageY - elm.offset().top;
            let xPos = e.pageX;
            let yPos = e.pageY;
            console.log(xPos, yPos);
            $(".white_background").css("display", "");
            let count = setInterval(function() {
                $('.collaborator-manager-list').css("left", elm.offset().left +80);
                $('.collaborator-manager-list').css("top", elm.offset().top + 35);
                $('.collaborator-manager-list').css("display", "");
                clearTimeout(count);
            }, 10);
        });
        $('.calendar_add_btn').click(function (e) {
            console.log(e.target.id);
            let elm = $(this);
            // var xPos = e.pageX - elm.offset().left;
            // var yPos = e.pageY - elm.offset().top;
            let xPos = e.pageX;
            let yPos = e.pageY;
            console.log(xPos, yPos);
            $(".white_background").css("display", "");
            let count = setInterval(function() {
                $('.collaborator-calendar-list').css("left", elm.offset().left +20);
                $('.collaborator-calendar-list').css("top", elm.offset().top + 80);
                $('.collaborator-calendar-list').css("display", "");
                clearTimeout(count);
            }, 10);
        });

    });
}