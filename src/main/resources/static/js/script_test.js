// 此為回到最上層按鈕的JS
import axios from "/js/axios.js";
{
    function buildVoteResult(voteResult){
        var i=0;
        var voteTitle = $("#voteTitle");
        voteTitle.append(voteResult.question);
        var resultValueTable = $("#resultValueTable");
        var optionTable = $("#optionsTable");
        let opTemplate;
        for(i = 0 ; i < (voteResult.voteCount).length ; i ++){
            if(voteEvent["voteResult"][voteEvent["keywords"][i]].length>5){
                opTemplate =
                    '<div class=" mt-3" style="height: 30px;font-size: 12px;line-height: 14px">'+
                    voteEvent["voteResult"][voteEvent["keywords"][i]]+
                    '</div>'
            }
            else{
                opTemplate =
                    '<div class=" mt-3" style="height: 30px">'+
                    voteEvent["voteResult"][voteEvent["keywords"][i]]+
                    '</div>'
            }
            optionTable.append(opTemplate);
        }
        let voterTemplate;
        let totalVoters=voteEvent.voter.length;
        let totAns;
        for(i = 0 ; i < voteEvent["keywords"].length ; i ++) {
            totAns = 0;
            for (let j = 0; j < voteEvent["voter"].length; j++) {
                if (voteEvent["voterAns"][voteEvent["voter"][j]] == voteEvent["keywords"][i]) {
                    totAns++;
                    console.log(totAns)
                }
            }
            totAns = totAns * 100 / totalVoters;
            voterTemplate =
                '<div class="progress mt-3" style="height: 30px">' +
                '<div class="progress-bar" style="width:' + totAns.toFixed(2) + '%;height:30px">' + totAns.toFixed(2) + '%</div>' +
                '</div>'
            resultValueTable.append(voterTemplate);
        }
    }
    function getVoteResult(){
        let url = "../get_voteResult"
        var data = sessionStorage.getItem("voteData");
        $.ajax({
            type: 'POST',
            url: url,
            data: data,
            dataType: 'json',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            success: function(data){
                if(data!=null){
                    let voteResult = JSON.parse(data);
                    console.log("voteResult：   "+voteResult);
                    console.log("voteCount：   "+voteResult.voteCount);
                    console.log("typeof：   "+typeof (voteResult.voteCount));
                }
                else
                    console.log("getVoteResult failed.");
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
                alert("some error");
            },
        });
    }
    $(document).ready(function (){
        console.log("-----------------------------------------")
        console.log(sessionStorage.getItem("voteData"));
        getVoteResult();
        setTimeout("getVoteResult();", 2000);

    })

}
//var intervalID = setInterval(function() {
//     alert('十秒鐘又到了！');
// }, 10000);