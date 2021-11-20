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
<title value="{room_id}+'번방'"></title>

<link rel="stylesheet" type="text/css" href="/css/room.css" />
<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<head>
        <%
                String userID = null;
                if (session.getAttribute("userID") != null) {
                        userID = (String) session.getAttribute("userID");
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
                        var fromID = '<%= userID %>';
                        var toID = '<%= toID %>';
                        var charContent = $('#chatContent').val();
                        $.ajax({
                                type: "POST",
                                url: "./ChatSubmitServlet",
                                data: {
                                        fromID: encodeURIComponent(fromID),
                                        toID: encodeURIComponent(toID),
                                        chatContent: encodeURIComponent(charContent),
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
        </body>
        </html>