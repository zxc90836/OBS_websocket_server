// 此為回到最上層按鈕的JS
{
    const video_focus = document.querySelector('.video-focus');
    function video_details_show(target) {
        let page = target;
        console.log(page.id);
        video_focus.innerHTML=page.id;
    }
    const videoCardContainer = document.querySelector('.video-container');

    let api_key = "AIzaSyCf7rHaZ9g7bJWfFvHCO8f8gX7eSlbp-Ww"
    let video_http = "https://www.googleapis.com/youtube/v3/videos?";
    let channel_http = "https://www.googleapis.com/youtube/v3/channels?";

    fetch(video_http + new URLSearchParams({
        key:api_key,
        part: 'snippet',
        chart: 'mostPopular',
        maxResults: 50,
        regionCode: 'tw'
    }))
    .then(res => res.json())
    .then(data =>{
        // console.log(data);
        data.items.forEach(item => {
            getChannelIcon(item);
        })
    })
    .catch(err => console.log(err));

    const getChannelIcon = (video_data) => {
        fetch(channel_http + new URLSearchParams({
            key: api_key,
            part: 'snippet',
            id: video_data.snippet.channelId
        }))
            .then(res => res.json())
            .then(data =>{
                video_data.channelThumbnail = data.items[0].snippet.thumbnails.default.url;
                makeVideoCard(video_data);
            })
    }
    const  makeVideoCard = (data) => {
        videoCardContainer.innerHTML +=`
            <div class="video" onclick="location.href ='https://youtube.com/watch?v=${data.id}'">
                <img src="${data.snippet.thumbnails.high.url}" class="thumbnail" alt="">
                    <div class="content">
                        <img src="${data.channelThumbnail}" class="channel-icon" alt="">
                        <div class="info">
                            <h4 class="title">${data.snippet.title}</h4>
                            <p class="channel-name">${data.snippet.channelTitle}</p>
                        </div>
                    </div>
            </div>
        `
    }
}