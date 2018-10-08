<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>EL</title>
</head>
<body>
    <h1>Expression Language 표기</h1>
    <pre>
        test : 
            http://localhost:8888/el/ex01.jsp
            
            
        - EL(Expression Language)은 콤마(.)와 대괄호([]) 등을 사용하여 객체의 프로퍼티나,
            리스트, 셋, 맵 객체의 값을 쉽게 꺼내고 설정하게 도와주는 문법이다.
            특히 값을 꺼낼 때는 OGNL 표기법을 사용한다.
        
        - OGNL? (Object Graph Navigation Language)
            객체의 프로퍼티 값을 가리키는 문법이다.
            파일의 경로처럼 객체에 포함된 객체를 탐색하여 값을 쉽세 죄회할 수 있다.
        
        - 문법
            $ { 객체명.프로퍼티명.프로퍼티명 }
            $ { 객체명["프로퍼티명"]["프로퍼티명"]["프로퍼티명"] }
        
        - EL 에서 사용할 수 있는 객체?
            pageContext:JSP의 pageContext 객체
            servletContext - ${ pageContext.servletContext.프로퍼티 명 }
                    =>  자바 코드 : pageContext.getServletContext().get프로퍼티();
            session - $ { pageContext.session.프로퍼티 명 }
                    => 예) $ { pageContext.session.name }
                        =>  pageContext.getSession().getName();
                        
            request - $ { pageContext.request.프로퍼티 명 }
            response
            param - $ { param.파라미터 명 }
                    => 자바 코드 : request.getParameter("파라미터")
            paramValues - $ { paramValues.파라미터 명 }
                    => request.getParameterValues("파라미터 명");
            header - $ { header.헤더 명 }
                    => request.getHeader("헤더 명");
            headerValues - $ { headerValues.헤더 명 }
                    => request.getHeaders("헤더 명");
            cookie - $ { cookie.쿠키 명 }
            initParam - $ { initParam.파라미터 명 }
            
            보관소에서 값을 꺼내는 문법
            pageScope - $ { pageScope.객체 이름 }
                    => pageContext.getAttribute("객체 이름");
            requestScope - $ { requestScope.객체 이름 }
                    => request.getAttribute("객체 이름");
                    
            sessionScope - $ { sessionScope.객체 이름 }
                    => session.getAttribute("객체 이름");
                        예)
                        
            applicationScope - $ { applicationScope.객체 이름 }
                    => application.getAttribute("객체 이름");
    </pre>
    
</body>
</html>