<%@ page import="org.springframework.boot.web.servlet.server.Session" %><%--
  Created by IntelliJ IDEA.
  User: PC
  Date: 2021-11-20
  Time: 오전 8:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<meta charset="UTF-8" content="text/html" http-equiv="Content-Type">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>${room_id}번방</title>

<link rel="stylesheet" type="text/css" href="/css/room.css" />
<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<head>
        <%
                String ID = null;
                if (session.getAttribute("ID") != null) {
                        ID = (String) session.getAttribute("ID");
                }
                String toID = null;
                if (request.getParameter("toID") != null) {
                        toID = (String) request.getParameter("toID");
                }
        %>

        <script type="text/javascript">
                function autoClosingAlert(selector, delay) {
                        var alert = $(selector).alert();
                        alert.show();
                        window.setTimeout(function() { alert.hide() }, delay);
                }
                function submitFunction() {
                        var fromID = '<%= ID %>';
                        var toID = '<%= toID %>';
                        var chatContent = $('#chatContent').val();
                        $.ajax({
                                type: "POST",
                                url: "./ChatSubmitServlet",
                                data: {
                                        fromID: encodeURIComponent(fromID),
                                        toID: encodeURIComponent(toID),
                                        chatContent: encodeURIComponent(chatContent),
                                },
                                success: function (result) {
                                        if(result == 1) {
                                                autoClosingAlert('#successMessage', 2000);
                                        }else if (result == 0) {
                                                autoClosingAlert('#dangerMessage', 2000);
                                        }else {
                                                autoClosingAlert('#warningMessage', 2000);
                                        }
                                }
                        });
                        $('#chatContent').val('');
                }
                var lastID = 0;
                function chatListFunction(type){
                        var fromID = '<%= ID %>';
                        var toID = '<%= toID %>';
                        $.ajax({
                                type: "POST",
                                url: "./ChatListServlet",
                                data: {
                                        fromID: encodeURIComponent(fromID),
                                        toID: encodeURIComponent(toID),
                                        listType:type
                                },
                                success:function (data){
                                        if(data == "") return;
                                        var parsed = JSON.parse(data);
                                        var result = parsed.result;
                                        for(var i = 0; i < result.length; i++){
                                                addChat(result[i][0].value, result[i][2].value, result[i][3].value);
                                        }
                                        lastID = Number(parsed.last);
                                }
                        })
                }
                function addChat(chatName, chatContent, chatTime){
                        $('#chatLog').append('<div class="row">' +
                                '<div class="col-lg-12">' +
                                '<div class="media'> +
                                        '<a class="pull-left" href="#">' +
                                '<img class="media-object img-circle" src="images/icon.png" alt="">' +
                                '</a>' +
                                '<div class="media-body">' +
                                chatName +
                                '<span class="small pull-right">' +
                                chatTime +
                                '</span>' +
                                '<h4>' +
                                '<p>' +
                                chatContent +
                                '</p>' +
                                '</div>' +
                                '</div>' +
                                '</div>' +
                                '</div>' +
                                '<hr>');
                        $('#chatLog').scrollTop($('#chatLog')[0].scrollHeight);
                }
                function getInfiniteChat() {
                        setInterval(function (){
                                chatListFunction(lastID);
                        }, 300);
                }
        </script>
</head>

<body>
        <div id="contentWrap">
        <div id="contentCover">
        <div id="roomWrap">
        <div id="roomList">
        <div id="roomHeader">MENU</div>
        <div id="roomSelect">
        </div>
        </div>
        </div>
        <div id="chatWrap">
                <div id="chatHeader">${room_id}번 방</div>
        <div id="chatLog">
        <div class="anotherMsg">
        </div>
        <div class="myMsg">
        </div>
        </div>
        <form id="chatForm">
        <input type="text" autocomplete="off" size="30" id="chatContent" placeholder=" 메시지를 입력하세요">
        <input type="submit" onclick="submitFunction()" value="보내기">
                <div class="alert alert-success" id="successMessage" style="display: none;">
                        <strong>메시지 전송 성공</strong>
                </div>
                <div class="alert alert-success" id="dangerMessage" style="display: none;">
                        <strong>메시지 전송 실패</strong>
                </div>
                <div class="alert alert-success" id="warningMessage" style="display: none;">
                        <strong>데이터 베이스 오류</strong>
                </div>
        </form>
        </div>
                <%
                        String messageContent = null;
                        if (session.getAttribute("messageContent") != null) {
                                messageContent = (String) session.getAttribute("messageContent");
                        }
                        String messageType = null;
                        if (session.getAttribute("messageType") != null) {
                                messageType = (String) session.getAttribute("messageType");
                        }
                        if (messageContent != null){
                %>
                <div class="modal fade" id="messageModal" tabindex="-1" role="dialog" aria-hidden="true">
                        <div class="vertical-alignment-helper">
                                <div class="modal-dialog vertical-align-center">
                                        <div class="modal-content" <% if(messageType.equals("오류 메시지")) out.println("panel-warning"); else out.println("panel-success"); %>
                                                <div class="modal-header panel-heading">
                                                        <button type="button" class="close" data-dismiss="modal">
                                                                <span aria-hidden="true">&times</span>
                                                                <span class="sr-only">Close</span>
                                                        </button>
                                                        <h4 class="modal-title">
                                                                <%= messageType %>
                                                        </h4>
                                                </div>
                                                <div class="modal-body">
                                                        <%= messageContent %>
                                                </div>
                                                <div class="modal-footer">
                                                        <button type="button" class="btn btn-primary" data-dismiss="modal">확인</button>
                                                </div>
                                        </div>
                                </div>
                        </div>
                </div>
                <script>
                        $('#messageModal').modal("show");

                </script>
                <%
                        session.removeAttribute("messageContent");
                        session.removeAttribute("messageType");
                        }
                %>
        <div id="memberWrap">
        <div id="memberList">
        <div id="memberHeader">MEMBER LIST</div>
        <div id="memberSelect"></div>
        </div>
        </div>
        </div>
        </div>
        <script type="text/javascript">
                $(document).ready(function () {
                        chatListFunction('ten');
                        getInfiniteChat()
                })
        </script>
</body>
</html>