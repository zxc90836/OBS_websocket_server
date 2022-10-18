{


    //jquery 點擊團隊模板
    $(document).ready(function (){
        var user = sessionStorage.getItem("user");
        console.log(user);
        var url = "http://127.0.0.1:55304/getColab?account="+user
        $.getJSON(url,function (data){
            console.log(data);
            $(".teams_container").empty();
            var num = 1;
            $.each(data, function(i,team){

                var teamName = team.replace(/@/g,"-").replace(/\./g,"-");
                console.log(teamName);
                var team_card = `
                <div class="col-4">
                    <div class="card select-team1 select-card" id = "${teamName}">
                        <div class="card-body">
                            <h4 class="card-title"style="text-align: center;">團隊${num++}</h4>
                            <p class="card-text" style="max-height: 25px;overflow: hidden">${team}</p>
                        </div>
                    </div>
                </div>
                `;
                $(".teams_container").append(team_card);
                $("#"+teamName).click(function (e) {
                    console.log(teamName);
                    sessionStorage.setItem("controll",team);
                });
            });
        });
    })
}