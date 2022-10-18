// 此為回到最上層按鈕的JS
import axios from "/js/axios.js";

{
    var voteEvent = {
        question: "想要主播選哪隻英雄",
        keywords:["a","b","c"],
        voteResult:{
            "a":"達瑞斯不是達瑞文也不是德萊厄斯更不是德萊文",
            "b":"厄薩斯",
            "c":"拉姆斯"
        },
        voteTime:600,
        pollAccount:"shabi94ni",
        voter:["花生一號","花生二號","花生三號"],
        voterAns: {
            "花生一號":"a",
            "花生二號":"b",
            "花生三號":"a"
        }
    }
    console.log(voteEvent)
    var Result = JSON.stringify(voteEvent);
    console.log(Result)

    var intervalID = setInterval(function() {
        alert('五秒鐘又到了！');
    }, 5000);

    $(document).ready(function (){
        var i=0;
        var voteTitle = $("#voteTitle");
        voteTitle.append(voteEvent["question"]);
        var resultValueTable = $("#resultValueTable");
        var optionTable = $("#optionsTable");
        let opTemplate;
        for(i = 0 ; i < voteEvent["keywords"].length ; i ++){
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
        for(i = 0 ; i < voteEvent["keywords"].length ; i ++){
            totAns=0;
            for(let j=0;j<voteEvent["voter"].length;j++){
                if(voteEvent["voterAns"][voteEvent["voter"][j]]==voteEvent["keywords"][i]){
                    totAns++;
                    console.log(totAns)
                }
            }
            totAns=totAns*100/totalVoters;
            voterTemplate =
                '<div class="progress mt-3" style="height: 30px">'+
                '<div class="progress-bar" style="width:' + totAns.toFixed(2) + '%;height:30px">'+totAns.toFixed(2)+'%</div>'+
                '</div>'
            resultValueTable.append(voterTemplate);
        }

    })







}
//var intervalID = setInterval(function() {
//     alert('十秒鐘又到了！');
// }, 10000);