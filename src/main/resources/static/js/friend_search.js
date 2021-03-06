$(function() {
    let inputVal;
    $("#keyword").autocomplete({
        source: function(request, response) {
            $.ajax({
                type: "POST",
                url: "/friend_search",
                data: JSON.stringify(inputVal={"id": request.term}),
                dataType: "json",
                contentType: "application/json; charset=utf-8;",
                success: function(data) {
                    response(
                        $.map(data, function(item) {
                            return {
                                label: item.id,
                                value: item.id
                            }
                        })
                    )
                },
                minLength: 3,
                autoFocus: true,
                delay: 500
            });
        }
    }).autocomplete("instance")._renderItem = function(ul, item) {
        return $('<li onclick=location.href="/'+item.value+'">')
            .append(
                '<span>'+item.value+'</span>'
                )
            .appendTo(ul);
    }
});