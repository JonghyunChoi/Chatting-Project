function lastMessageAjax(){
    $.ajax({
        method: "POST",
        url: "/lastMessage",
        cache: false,
        async: false,
        success: function(data){
            if(lastMessageTime < data) {
                readAjax(lastMessageTime);
                lastMessageTime = data;
            }else{
                lastMessageTime = data;
            }
        }
    });
}