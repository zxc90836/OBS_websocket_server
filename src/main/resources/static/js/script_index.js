// 此為回到最上層按鈕的JS
{
    function showVotePage() {
        var voteResultPage;
        voteResultPage = window.open('votePage.html', 'open', 'height=500, width=700');
        //voteResultPage = window.open('index.html', 'open', 'height=500, width=500');
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
        var Key = sessionStorage.getItem("controll");
        //var pending_url = "http://140.121.196.20:55304/OBS_websocket/get_scenes?key="+Key;
        var pending_url= "../OBS_websocket/get_scenes?key="+Key;
        var insert_pending_HTML = "";

        $("#sceneList").html("");
        $.getJSON(pending_url,function(result){
            $.each(result,function(index,value){
                var insert_pending_HTML = "";
                insert_pending_HTML +=
                    '<a id="16" href="#" class="list-group-item list-group-item-action changeSceneButton">'+value+'</a>';

                $("#sceneList").append(insert_pending_HTML);
            });
            $(".changeSceneButton").click(function (e) {
                //var change_url = "http://140.121.196.20:55304/OBS_websocket/change_scene?key=";
                let change_url = "../OBS_websocket/change_scene?key=";
                $.get(change_url+ Key +"&scene="+e.target.text);
                console.log(change_url+ Key +"&scene="+e.target.text);
                //$.get("http://140.121.196.20:55304/OBS_websocket/change_scene?key=4908795&scene=場景");
            });
        });

    });

}