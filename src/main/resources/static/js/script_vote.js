// 此為回到最上層按鈕的JS
{
    //jquery
    $(document).ready(function (){
        var i = 2;
        var votingSelectionTable = $("#votingSelectionTable")

        $("#addSelectionButton").click(function (e) {
            let votingSelectionTemplate =
                ' <div class="input-group mb-1">\n' +
                '   <div class="close_button">\n' +
                '       <span></span>\n' +
                '       <span></span>\n' +
                '   </div>'+
                '   <span class="input-group-text">選項編號</span>\n' +
                '   <input id="response'+ i +'" type="text" class="form-control voteName_input">\n' +
                '   <span class="input-group-text">選項內容</span>\n' +
                '   <input id="attr'+ i +'" type="text" class="form-control voteName_input">\n' +
                ' </div>'
            votingSelectionTable.append(votingSelectionTemplate);
            i=i+1;
            $(".close_button").click(function (e) {
                e.target.closest('.input-group').remove();
            });
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
        votingInfo.timeLimit   = sec; // 尚未建立
        //votingInfo.pollAccount = sessionStorage.getItem("user"); // 尚未建立
        votingInfo.pollAccount =sessionStorage.getItem("controll");
        votingInfo.question  = $('#votingQuestion').val();
        let tempMap = new Map();
        //console.log($("#serial" + serial).val());
        while($('#response' + serial).length > 0) {
            let key = $('#response' + serial).val();
            let value = $('#attr' + serial).val();
            console.log(key+"   "+value);
            tempMap.set(key,value);
            serial++;
            console.log(Object.fromEntries(tempMap));
        }
        votingInfo.legalResponse = Object.fromEntries(tempMap);
        // pop out from while (Name doesn't exist)TEST321


        return JSON.stringify(votingInfo);
    }

    $(document).ready(function (){
        console.log($('#votingQuestion').val())
        $('#startVoting').click(function (){
            var data = createVoting();
            sessionStorage.setItem("voteData",data);
            console.log(data);
            url = "../start_vote";
            $.ajax({
                type: "POST",
                url: url,
                data:data,
                dataType: 'json',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                success: function(re){
                    if(re == true){
                        alert("投票發起成功，請前往投票結果查看。");
                    }
                    else
                        alert("投票發起失敗，請重新嘗試");
                },
                error: function (thrownError) {
                    alert(thrownError);
                }
            });
        })
    })
}