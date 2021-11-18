function readAjax(compareTime){
    $.ajax({
        method: "POST",
        url: "/read",
        dataType: "json",
        cache: false,
        async: false,
        data: {
        "lastMessage": compareTime
        },
        success: function(data){
            if(data.length == 0) {
                return;
            }
            else {
                $.each(data, function(index, entry) {})
            }
        }
    });
}