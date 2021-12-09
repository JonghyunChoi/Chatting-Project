function follow(id, friend_id) {
    let friendInfoDTO;

    $.ajax({
        type: "POST",
        url: "/follow",
        data: JSON.stringify(friendInfoDTO={
            "id": id,
            "friend_id": friend_id
            }),
        dataType: "json",
        contentType: "application/json; charset=utf-8;",
    })
        .always(function() {
            $('#follow').replaceWith(
                $('<button ' + 'onclick=unFollow("' + id + '","' + friend_id + '") ' +
                    'id="unFollow"' + 'class="btn btn-secondary float-end"' + 'type="button">언팔로우' +
                    '</button>')
            );
        })
}

function unFollow(id, friend_id) {
    let friendInfoDTO;

    $.ajax({
        type: "POST",
        url: "/unfollow",
        data: JSON.stringify(friendInfoDTO={
            "id": id,
            "friend_id": friend_id
        }),
        dataType: "json",
        contentType: "application/json; charset=utf-8;",
    })
        .always(function() {
            $('#unFollow').replaceWith(
                $('<button ' + 'onclick=follow("' + id + '","' + friend_id + '") ' +
                    'id="follow"' + 'class="btn btn-primary float-end"' + 'type="button">팔로우' +
                    '</button>')
            );
        })
}