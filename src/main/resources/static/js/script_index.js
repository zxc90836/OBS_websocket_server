// 此為回到最上層按鈕的JS
{
    function showVotePage() {
        var voteResultPage;
        voteResultPage = window.open('votePage.html', 'open', 'height=500, width=700');
        //voteResultPage = window.open('index.html', 'open', 'height=500, width=500');
    }
    function showSchedule(){
        $("#schedule_container").html("");
        let url = "../getSchedule?team="+sessionStorage.getItem("controll");
        $.getJSON(url,function(result){
            $.each(result,function(index,value){
                console.log("日程"+value);
                var insert_schedule_HTML = "";
                insert_schedule_HTML += `
                    <div class="bulletinBoard">
                        <div class="info">
                            <h4 class="content">${value.describe}</h4>
                            <span class="time">${value.date}   ${value.startTime}~${value.endTime}</span>
                        </div>
                        <div id="bulletinBoard-1" class="edit_button_collaborator">
                            <span></span>
                            <span></span>
                            <span></span>
                        </div>
                    </div>
                `;
                $("#schedule_container").append(insert_schedule_HTML);
            });
        });
    }
    function showMember(){
        console.log("showMember");
        $("#collaboratorRoom_body").html("");
        let url = "../getMember?team="+sessionStorage.getItem("controll");
        $.getJSON(url,function(result){

            $.each(result,function(index,value) {
                var insert_member_HTML = "";
                insert_member_HTML += `
                <div class="collaborator">
                    <img src="../picture/people.png" class="channel-icon" alt="">
                    <div class="info">
                        <h4 class="name">${value.memberName}</h4>
                        <span class="${value.remoteControl?"active":""} authority">遠端控制</span>
                        <span class="${value.dataAnalysis?"active":""} authority">數據分析</span>
                        <span class="${value.teamManagement?"active":""} authority">團隊管理</span>
                    </div>
                    <div id="collaborator1_channelID" class="edit_button_collaborator">
                        <span></span>
                        <span></span>
                        <span></span>
                    </div>
                </div>`;
                $("#collaboratorRoom_body").append(insert_member_HTML);
            });
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
        });
    }
    function getStreamSource(){
        let url = "../getMember?team="+sessionStorage.getItem("controll");
        $.getJSON(url,function(result){
            $.each(result,function(index,value) {
                var insert_member_HTML = "";
                insert_member_HTML += `
                <div class="collaborator">
                    <img src="../picture/people.png" class="channel-icon" alt="">
                    <div class="info">
                        <h4 class="name">${value.memberName}</h4>
                        <span class="${value.remoteControl?"active":""} authority">遠端控制</span>
                        <span class="${value.dataAnalysis?"active":""} authority">數據分析</span>
                        <span class="${value.teamManagement?"active":""} authority">團隊管理</span>
                    </div>
                    <div id="collaborator1_channelID" class="edit_button_collaborator">
                        <span></span>
                        <span></span>
                        <span></span>
                    </div>
                </div>`;
                $("#collaboratorRoom_body").append(insert_member_HTML);
            });
        });
    }
    //jquery
    $(document).ready(function (){
        $(".page-button").click(function (e) {
            $(".page1").css("display","none")
            $(".page2").css("display","none")
            $(".page3").css("display","none")
            $(".page4").css("display","none")
            //console.log(e.target.id);

            $("." + e.target.id).css("display","")
            /*$(".card_show").css("display", "");
            $(".black_background").css("display", "");*/
        });
        let user = JSON.parse(sessionStorage.getItem("user"));
        $("#userName").html(user.userName);
        var Key = sessionStorage.getItem("controll");
        //var pending_url = "http://140.121.196.20:55304/OBS_websocket/get_scenes?key="+Key;
        var pending_url= "../OBS_websocket/get_scenes?key="+Key;
        var insert_pending_HTML = "";
        showSchedule();
        showMember();

        stream_displayBox
        $(".sceneList").html("");
        $.getJSON(pending_url,function(result){
            $.each(result,function(index,value){
                console.log(value);
                var insert_pending_HTML = "";
                insert_pending_HTML +=
                    '<a id="16" href="#" class="list-group-item list-group-item-action changeSceneButton">'+value+'</a>';

                $(".sceneList").append(insert_pending_HTML);
            });
            $(".changeSceneButton").click(function (e) {
                //var change_url = "http://140.121.196.20:55304/OBS_websocket/change_scene?key=";
                let change_url = "../OBS_websocket/change_scene?key=";
                $.get(change_url+ Key +"&scene="+e.target.text);
                console.log(change_url+ Key +"&scene="+e.target.text);
                //$.get("http://140.121.196.20:55304/OBS_websocket/change_scene?key=4908795&scene=場景");
            });
        });
        $("#log_out_btn").click(function (){
            sessionStorage.removeItem("user");
            sessionStorage.removeItem("control");
            window.location.href="../html/loginPage.html.html";
        })
        $("#add_schedule_btn").click(function (){
            let team = sessionStorage.getItem("controll");
            let date = $("#schedule_date").val();
            let describe = $("#schedule_contain").val();
            let streamSchedule = "";
            let startTime = $("#schedule_startTime").val();
            let endTime = $("#schedule_endTime").val();
            let url = "../setSchedule";
            let data = {};
            data.team = team;
            data.date = date;
            data.describe = describe;
            data.streamSchedule = streamSchedule;
            data.startTime = startTime;
            data.endTime = endTime;
            let jsonData = JSON.stringify(data);
            $.ajax({
                type: 'POST',
                url: url,
                data: jsonData,
                dataType: 'json',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                success: function(data){
                    if(data==true){
                        console.log(data);
                        showSchedule();
                    }
                    else
                        console.log("login failed.");
                },
                error: function(XMLHttpRequest, textStatus, errorThrown) {
                    alert("some error");
                },
            });
        });
        $("#add_member_btn").click(function (){
            let team = sessionStorage.getItem("controll");;
            let memberName = $("#add_member_account").val();
            let remoteControl = true;
            let dataAnalysis = true;
            let teamManagement = false;
            let data = {};
            data.team = team;
            data.memberName = memberName;
            data.remoteControl = remoteControl;
            data.dataAnalysis = dataAnalysis;
            data.teamManagement = teamManagement;
            let jsonData = JSON.stringify(data);
            let url = "../addMember"
            $.ajax({
                type: 'POST',
                url: url,
                data: jsonData,
                dataType: 'json',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                success: function(data){
                    if(data==true){
                        console.log(data);
                        showMember();
                    }
                    else
                        console.log("login failed.");
                },
                error: function(XMLHttpRequest, textStatus, errorThrown) {
                    alert("some error");
                },
            });
        })
    });

}