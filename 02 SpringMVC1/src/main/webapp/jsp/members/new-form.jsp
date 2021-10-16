<%--
  Created by IntelliJ IDEA.
  User: HJ
  Date: 2021-10-16
  Time: 오후 11:16
  To change this template use File | Settings | File Templates.
--%>
<%--jsp 파일이라는 것을 알려주는 줄 꼭 있어야함--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/jsp/members/save.jsp" method="post">
    username: <input type="text" name="username" />
    age: <input type="text" name="age" />
    <button type="submit">전송</button>
</form>
</body>
</html>
