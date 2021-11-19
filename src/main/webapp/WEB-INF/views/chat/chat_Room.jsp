<%@ page import="org.springframework.boot.web.servlet.server.Session" %><%--
  Created by IntelliJ IDEA.
  User: PC
  Date: 2021-11-20
  Time: 오전 8:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
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
        <meta charset="UTF-8" content="text/html" http-equiv="Content-Type">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title value="{room_id}+'번방'"></title>

        <link rel="stylesheet" type="text/css" href="/css/room.css" />
        <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
        <script type="text/javascript"></script>
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
        <input type="text" autocomplete="off" size="30" id="message" placeholder=" 메시지를 입력하세요">
        <input type="submit" value="보내기">
        </form>
        </div>
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