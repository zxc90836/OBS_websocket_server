{
    $(document).ready(function (){
        $("#register_btn").click(function (){
            var account = $("#account").val();
            var password = $("#password").val();
            var ytAccount = $("#YT_account").val();
            var url = `http://127.0.0.1:55304/signUp`;
            var data = {};
            data.userName = account;
            data.password = password;
            data.youtubeAccount = ytAccount;
            var jsonData = JSON.stringify(data);

            console.log(jsonData);
            $.ajax({
                type: 'POST',
                url: url,
                data: jsonData,
                dataType: 'json',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                success: function(msg){
                    alert( "Data Saved: " + msg );
                },
                error: function(XMLHttpRequest, textStatus, errorThrown) {
                    alert("some error");
                },
            });
        })
    })

}