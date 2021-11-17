function lastMessageAjax(){
    $.ajax({
        method: "POST",
        url: "/lastMessage",
        cache: false,
        async: false,
        success: function(data){
            if(lastDateTime < data) {
                readAjax(lastDateTime);
                lastDateTime = data;
            }else{
                lastDateTime = data;
            }
        }
    });
}