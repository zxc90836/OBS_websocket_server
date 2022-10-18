// 此為回到最上層按鈕的JS
{
    //jquery
    $(document).ready(function (){
        var i = 2;
        var votingSelectionTable = $("#votingSelectionTable")

        $("#addSelectionButton").click(function (e) {
            let votingSelectionTemplate =
                ' <div class="input-group mb-1">\n' +
                '   <span class="input-group-text">選項編號</span>\n' +
                '   <input id="response'+ i +'" type="text" class="form-control voteName_input">\n' +
                '   <span class="input-group-text">選項內容</span>\n' +
                '   <input id="attr'+ i +'" type="text" class="form-control voteName_input">\n' +
                ' </div>'
            votingSelectionTable.append(votingSelectionTemplate);
            i=i+1;
        });

    })
    function showVoteResultPage() {
        var voteResultPage;
        voteResultPage = window.open('voteResultPage.html', 'open', 'left=500, top=500, height=500, width=500');
        //voteResultPage = window.open('index.html', 'open', 'height=500, width=500');
    }

    function createVoting() {
        let serial = 1; // 流水號
        let responseArr = [];
        let votingInfo = {}; // Object

        var sec = parseInt($('#timeLimit').val()) * 60;
        votingInfo.timeLimit   = sec;
        votingInfo.pollAccount = sessionStorage.getItem("user");
        votingInfo.pollAccount = "user";
        votingInfo.question  = $('#votingQuestion').val();
        votingInfo.legalResponse = new Map;
        //console.log($("#serial" + serial).val());
        while($('#response' + serial).length > 0) {
            console.log(typeof responseArr);
            votingInfo.legalResponse.set($('#response' + serial).val(),$('#attr' + serial).val())
            serial++;
        }
        let obj = {};
        for(let [k,v] of votingInfo.legalResponse) {
            obj[k] = v;
        }
        votingInfo.legalResponse = obj;
        //pop out from while (Name doesn't exist)TEST321
        console.log(votingInfo)
        return votingInfo;
    }

    $(document).ready(function (){
        console.log($('#votingQuestion').val())

        $('#startVoting').click(function (){
            var data = createVoting();
            data = JSON.stringify(data);
            console.log(data);
            url = "/OBS_websocket/start_vote";
            console.log("cccc:"+url)
            $.ajax({
                type: "POST",
                url: url,
                data: data,
                contentType: "application/json",
                success: function(re){
                    alert(re);
                },
                error: function (thrownError) {
                    alert(thrownError);
                }
            });
        })
    })
}