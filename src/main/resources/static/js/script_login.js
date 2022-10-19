{
    $(document).ready(function (){

        $("#login_btn").click(function (){
            var account = $("#account").val();
            var password = $("#password").val();
            var data = {};
            data.userName = account;
            data.password = password;
            var jsonData = JSON.stringify(data);
            var url = `http://127.0.0.1:55304/login`;
            console.log(url);
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
                    if(data!=null){
                        console.log(data);
                        window.sessionStorage.setItem("user", JSON.stringify(data));
                        window.location.href="../html/teamSelectPage.html";
                    }
                    else
                        console.log("login failed.");
                },
                error: function(XMLHttpRequest, textStatus, errorThrown) {
                    alert("some error");
                },
            });
        })
    })

}