$(function(){
    setInterval(lastMessageAjax, 1000);

    $('#message').focus();

    $('#message').keyDown(function(key)
        {
        if(key.keyCode == 13){
            if(key.shiftKey){

            }else{
                writeAjax('N');
                return false;
            }
        }
    });

    $('#submit').on("click", function(){
        writeAjax('N');
    });
});