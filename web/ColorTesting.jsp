<%--
  Created by IntelliJ IDEA.
  User: student
  Date: 24.10.2017
  Time: 11:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Color Testing</title>
</head>
<%
    String bgColor = request.getParameter("bgColor");
    Boolean isChosen;
    if (bgColor!=null){isChosen = true;} else {isChosen = false; bgColor = "BLUE";}
%>
<body bgcolor="<%=bgColor%>">
<h1>Color testing</h1>
<%
    if (isChosen){out.println("Chosen background color is " + bgColor + ".");}else{
        out.println("Default background color is white ");
    }
%>
</body>
</html>
