{
    $(document).ready(function (){

        $("#login_btn").click(function (){
            var account = $("#account").val();
            var password = $("#password").val();

            var url = `http://127.0.0.1:55304/login?account=${account}&password=${password}`;
            console.log(url);
            $.getJSON(url, function(data){
                console.log(data);
                if(data==true){
                    console.log("ddd");
                    window.sessionStorage.setItem("user", account);
                    window.location.href="../html/teamSelectPage.html";
                }
                else
                    console.log("ttt");
            });
        })
    })

}