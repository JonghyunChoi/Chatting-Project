function exitChatRoom(_room_id, _id) {
    let room_id = _room_id;
    let id = _id;

    let chatRoomUserInfoDTO = {
        "room_id": room_id,
        "id": id
    };

    $.ajax({
        type: "POST",
        url: "/chat/exitRoom",
        data: JSON.stringify(chatRoomUserInfoDTO),
        contentType: "application/json; charset=utf-8;",
        success: function () {
            location.href='/chat/list'
        }
    });
}

function submitMessage(_room_id, _id) {
    let room_id = _room_id;
    let id = _id;
    let chat_content = $('#chatContent').val();
    let file_url = null;
    let chat_type = "text";
    let chatRoomContentDTO = {
        "room_id": room_id,
        "id": id,
        "chat_content": chat_content,
        "file_url": file_url,
        "chat_type": chat_type
    };

    if(chat_content != '') {
        $.ajax({
            type: "POST",
            url: "/chat/submit_message",
            data: JSON.stringify(chatRoomContentDTO),
            contentType: "application/json; charset=utf-8;"
        });
    }

    $('#chatContent').val(''); // 내용 초기화
}

let last_msg = 0;
let first_set = 0;

function loadMessage(_room_id, _id, get_msg) {
    let room_id = _room_id;
    let id = _id;
    let chatRoomContentDTO = {
        "room_id": encodeURIComponent(room_id),
        "id": encodeURIComponent(id)
    };
    $.ajax({
        type: "POST",
        url: "/chat/load_message",
        data: JSON.stringify(chatRoomContentDTO),
        dataType: "json",
        contentType: "application/json; charset=utf-8;",
        success: function(data) {
            if(data === "") return;

            get_msg = Number(data.length);

            if(last_msg < get_msg && first_set == 0) {
                for(let i = 0; i < data.length; i++) {
                    if(data.length > 0) {
                        addChatHtml(id, data[i].id, data[i].nickname, data[i].chat_content, data[i].chat_date, data[i].chat_type);
                    }
                }
            } else {
                for(let i = last_msg; i < data.length; i++) {
                    if(data.length > 0) {
                        addChatHtml(id, data[i].id, data[i].nickname, data[i].chat_content, data[i].chat_date, data[i].chat_type);
                    }
                }
            }

            first_set = 1;
            last_msg = Number(data.length);
        }
    });
}

function addChatHtml(_myID, id, nickname, content, time, type) {
    let myID = _myID;

    if(type === 'notice') {
        $('#chatLog').append(
            '<div class="noticeMsg">' +
            '<p>' + ' ['+ time +'] ' + '<strong>' + nickname + '(' + id  + ')' + '</strong>' + content + '</p>' +
            '</div>'
        );
    } else if(myID === id) {
        $('#chatLog').append(
            '<div class="myMsg">' +
            '<p>' + '<strong>' + nickname + '(' + id  + ')' + '</strong>' + ' ['+ time +']' + '</p>' +
            '<p>' + content + '</p>' +
            '</div>'
        );
    } else {
        $('#chatLog').append(
            '<div class="anotherMsg">' +
            '<p>' + '<strong>' + nickname + '(' + id  + ')' +'</strong>' + ' ['+ time +']' + '</p>' +
            '<p>' + content + '</p>' +
            '</div>'
        );
    }
    $('#chatLog').scrollTop($('#chatLog')[0].scrollHeight);
}

let last_list = 0;
let first_enter = 0;
let memberIDList = [];

function loadMemberList(_room_id, _id, get_memberList) {
    let room_id = _room_id;
    let id = _id;
    let chatRoomUserInfoDTO = {
        "room_id": encodeURIComponent(room_id),
        "id": encodeURIComponent(id)
    };
    $.ajax({
        type: "POST",
        url: "/chat/load_member",
        data: JSON.stringify(chatRoomUserInfoDTO),
        dataType: "json",
        contentType: "application/json; charset=utf-8;",
        success: function(data) {
            if(data === "") return;


            let delMemberIDList = [];
            get_memberList = Number(data.length);

            if(last_list < get_memberList && first_enter == 0) { // 처음 접속했을 때 + DB 보다 표기되는 멤버 수가 적을 경우 갱신
                for(let i = 0; i < data.length; i++) {
                    if(data.length > 0) {
                        memberIDList[i] = data[i].id;
                        addMemberListHtml(data[i].id, data[i].nickname)
                    }
                }
            } else {
                for (let i = last_list; i < data.length; i++) { // DB 보다 표기되는 멤버 수가 적을 경우
                    if (data.length > 0) {
                        memberIDList[i] = data[i].id;
                        addMemberListHtml(data[i].id, data[i].nickname)
                    }
                }
            }

            if(last_list > data.length) { // DB 보다 표기되는 멤버 수가 많을 경우
                for (let i = 0; i < data.length; i++) {
                    delMemberIDList[i] = data[i].id;
                }
                let filterMemberIDList = memberIDList.filter(x => !delMemberIDList.includes(x));

                for(let i = 0; i < filterMemberIDList.length; i++) {
                    if(data.length > 0) {
                        delMemberHtml(filterMemberIDList[i]);
                    }
                }
            }
            first_enter = 1;
            last_list = Number(data.length);
        }
    });
}

function addMemberListHtml(id, nickname) {
    $('#memberSelect').append(
        '<div id="' + id + '">' +
        '<span>' + '<strong>' + nickname + '(' + id  + ')' + '</strong>' + '</span>' +
        '</div>'
    );
}

function delMemberHtml(id) {
    $("div").remove("#"+id);
}

function getInfiniteChatLoad(_room_id, _id) {
    let room_id = _room_id;
    let id = _id;

    setInterval(function (){
        loadMessage(room_id, id, last_msg);
        loadMemberList(room_id, id, last_list);
    }, 500);
}