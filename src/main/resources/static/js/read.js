function readAjax(compareTime){
    $.ajax({
        method: "POST",
        url: "/read",
        dataType: "json",
        cache: false,
        async: false
    })
}