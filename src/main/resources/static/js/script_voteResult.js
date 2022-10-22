{
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