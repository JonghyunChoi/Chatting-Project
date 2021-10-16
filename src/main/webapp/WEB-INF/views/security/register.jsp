<!DOCTYPE html><%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
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
    <h1>Chat Register</h1>
</div>

<div>
    <form action="/createAccount" method="post">
        ID : <input type="text" name="id" value="${dto.id}">
        <p>${id_error_message}</p> <br />
        PW : <input type="text" name="password" value="${dto.password}">
        <p>${pwd_error_message}</p> <br />
        Nickname : <input type="text" name="nickname" value="${dto.nickname}">
        <p>${nickname_error_message}</p> <br />
        <input type="submit" value="가입">
        <a href="/login"><input type="button" value="취소" /></a> <br />
        <a href="/">홈으로</a> <br />
    </form>
</div>

</body>
</html>