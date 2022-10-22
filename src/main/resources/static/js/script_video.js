// 此為回到最上層按鈕的JS
{
    const video_focus = document.querySelector('.video-focus');
    const videoCount = document.querySelector('#videoCount');
    const viewCount = document.querySelector('#viewCount');
    const subscriberCount = document.querySelector('#subscriberCount');
    const estimatedRevenue = document.querySelector('#estimatedRevenue');
    //http://127.0.0.1:55304/getAllVideoFromDB?key=ethanhuang6@gmail.com
    //http://127.0.0.1:55304/getVideoFromDB?key=hdxhntMgNcM
    let url = "../getAllVideoFromDB?key=ethanhuang6@gmail.com";
    let channel_data_url = "../get_channelData?key=ethanhuang6@gmail.com"
    function video_details_show(target) {
        let page = target;
        console.log(page.id);
        let targetUrl = "../getVideoFromDB?key=" + page.id;
        console.log(targetUrl);
        $.getJSON(targetUrl,function(result) {
            // $.each(result, function (index, value) {
                let insert_member_HTML = "";
                insert_member_HTML += `
                <img src="${result.imgURL}" class="thumbnail" alt="">
                    <div class="content">
                        <div class="info">
                            <h4 class="title"></h4>
                            <div class="p-2 bg-white text-black rounded">
                                <h3>影片標題 : ${result.title}</h3>
                            </div>
                            <div class="mt-1 p-3 bg-white text-black rounded">
                                <h3>敘述:</h3>
                                <p>${result.description}</p>
                            </div>
                            <div class="video-details-table">
                                <div class="video-card card">
                                    <p>上傳狀態</p>
                                    <span>${result.uploadStatus}</span>
                                </div>
                                <div class="video-card card">
                                    <p>點讚人數</p>
                                    <span>${result.likeCount}</span>
                                </div>
                                <div class="video-card card">
                                    <p>討厭人數</p>
                                    <span>${result.dislikeCount}</span>
                                </div>
                                <div class="video-card card">
                                    <p>觀看人數</p>
                                    <span>${result.viewCount}</span>
                                </div>
                                <div class="video-card card">
                                    <p>留言數</p>
                                    <span>${result.commentCount}</span>
                                </div>
                                <!--                    <div class="video-card card">-->
                                <!--                      <p>tags</p>-->
                                <!--                      <span>4315</span>-->
                                <!--                    </div>-->
                            </div>
                        </div>
                    </div>
                    `;
                video_focus.innerHTML = ""
                $(".video-focus").append(insert_member_HTML);
            // });
        });
    }
    //剛進頁面時刷新
    $.getJSON(channel_data_url,function(result) {
        // $.each(result, function (index, value) {
            videoCount.innerHTML="${result.videoCount}";
            viewCount.innerHTML="${result.viewCount}";
            subscriberCount.innerHTML="${result.subscriberCount}";
            estimatedRevenue.innerHTML="${result.estimatedRevenue}";
        // });
    });
    $.getJSON(url,function(result) {
        $.each(result, function (index, value) {
            let insert_member_HTML = "";
            insert_member_HTML += `
                    <div class="video" id="${value.id}" onclick="video_details_show(this)">
                        <img src="${value.imgURL}" class="thumbnail" alt="">
                        <div class="content">
                            <div class="info">
                                <h4 class="title">${value.title}</h4>
                            </div>
                        </div>
                    </div>
                    `;
            $(".video-container").append(insert_member_HTML);
        });
    });
}