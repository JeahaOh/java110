<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSP</title>
</head>
<body>
    <h1>스크립트릿(scriptlet) 응용</h1>
<%  //  _jspService()메서드 안에 자바 코드를 작성한다는 것을 절대 잊지 말자.
/*...*/
//  <!-- 엥? -->
    String[] names = {"JEJE", "EKEK", "MJMJ", "PP"};
%>
<ul>
<%for (String name : names) { %>
    <li><%out.print(name);%></li>
<%} %>
</ul>
</body>
</html>
<!--
JSP 공부하는 tip :
    war 파일을 tomcat 서버로 돌릴 경우 :
apache-tomcat-9.0.12/work/Catalina/localhost/manager/org/apache/jsp/WEB_002dINF/jsp
    eclipse로 tomcat 서버 돌릴 경우 : 
org.eclipse.wst.server.core/tmp0/work/Catalina/localhost/ROOT/org/apache/jsp/jsp
    dir에서 컴파인 된 class 파일이나 java파일을 확인한다.
-->