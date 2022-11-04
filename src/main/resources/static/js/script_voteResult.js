{
    function getVoteResult(){
        let url = "../get_voteResult?key="+sessionStorage.getItem("controll");
        $.get(url, function(result){
            let info = JSON.parse(result);
            $("#voteTitle").html(info.question);
            $("#optionsTable").html("");
            $("#resultValueTable").html("");
            let allVoteCount = 0;
            let voteCount = info.voteCount
            for (const key in voteCount) {
                allVoteCount += voteCount[key];
                console.log(key + "  "+voteCount[key]);
            }
            for (const key in voteCount) {
                let option = "";
                let resultValue = "";
                option +=`
                    <div class=" mt-3" style="height: 30px">
                        ${key}
                    </div>`
                let num = Number(voteCount[key]/allVoteCount);
                resultValue = `
                    <div class="progress mt-3" style="height: 30px">
                        <div class="progress-bar" style="width:${num*100}%;height:30px">${num*100}%</div>
                    </div>`
                $("#optionsTable").append(option);
                $("#resultValueTable").append(resultValue);
            }
            return info.endFlag;
        });
    }
    function getVoteResultLoop(){
        let url = "../get_voteResult?key="+sessionStorage.getItem("controll");
        let info;
        let flag = false;
        $.get(url, function(result){
            info = JSON.parse(result);
            let allVoteCount = 0;
            let voteCount = info.voteCount
            console.log(info)
            $("#resultValueTable").html("");
            for (const key in voteCount) {
                allVoteCount += voteCount[key];
                console.log(key + "  "+voteCount[key]);
            }
            for (const key in voteCount) {
                let resultValue = "";
                let num = Number(voteCount[key]/allVoteCount);
                resultValue = `
                    <div class="progress mt-3" style="height: 30px">
                        <div class="progress-bar" style="width:${num*100}%;height:30px">${num*100}%</div>
                    </div>`
                $("#resultValueTable").append(resultValue);
            }
            flag = info.endFlag;
        });
        return flag;
    }
    $(document).ready(async function () {
        console.log("-----------------------------------------")
        console.log(getVoteResult());
        let flag = false;
        console.log(flag);
        console.log(typeof (flag))
        while (flag != true) {
            await new Promise((resolve, reject) => {//進入等待
                setTimeout(function () {
                    resolve();//繼續往下執行
                    flag = getVoteResultLoop();
                    console.log(flag)
                    console.log(flag != true)
                }, 4000);
            })
        }
    })
}