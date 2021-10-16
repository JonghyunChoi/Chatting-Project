<!DOCTYPE html><%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Login</title>

    <link rel="stylesheet" type="text/css" href="/css/form.css" />
</head>
<body>

<div>
    <h1>Chat Login</h1>
</div>

<div>
    <form action="/login" method="post">
        ID : <input type="text" name="id"> <br />
        PW : <input type="text" name="password"> <br />
        <c:if test="${param.error != null}">
            <p>${error_message}</p>
        </c:if>
        <input type="submit" value="로그인"> <br />
        <a href="/register">가입하기</a> <br />
        <a href="/">홈으로</a> <br />
    </form>
</div>

</body>
</html>
