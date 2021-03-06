/*  ServletContext 보관소의 데이터 꺼내기.
    => 서블릿이나 필터, 리스너를 실행하는 중에 필요한 값들을
        보관소에 저장하고 꺼내 쓸 수 있다.
    => 보관소
        1) ServletContext 보관소
            - 생성 시점 : 웹 애플리 케이션이 시작될 때 삿 웹애플리케이션 마다 한 개의 보관소가 생성됨.
            - 소멸 시점 : 웸에플리케이션이 종료될때.
            - 저장 대상
                - 서블릿들이 공유하는 객체를 주로 보관함.
                ex) DAO, 서비스 객체들.
        2) HttpSession 보관소
            - 생성 시점 : 각 클라이언트에 대해 세션을 시작할 때 마다.
            - 소멸 시점 : 각 클라이언트에 대해 세션을 종료할 때 마다.
            - 저장 대상
                - 클라이언트 전용 정보를 보관함.
                ex) 로그인 정보, 페이지 사이에 공유하는 정보.
        3) ServletRequest 보관소
            - 생성 시점 : 요청이 들어올 때 마다.
            - 소멸 시점 : 응답을 완료할 때 마다.
            - 저장 대상
                - 한 요청을 처리하는 서블릿들이 공유하는 정보.
        4) PageContext 보관소.
            - 생성 시점 : JSP가 실행될 때 마다.
            - 소멸 시점 : JSP 실행을 마쳤을 때 마다.
            - 저장 대상
                - JSP와 태그 핸들러가 공유하는 정보. 
    
 */

package bitcamp.java110.ex07;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ex07/servlet02")
public class Servlet02 extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Override
    public void service(
            HttpServletRequest req,
            HttpServletResponse res)
                    throws ServletException, IOException {
        
        //  http://localhost:8888/ex07/servlet02
        
        res.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = res.getWriter();
        
        out.println("/ex07/servlet02 실행.");
        
        //  ServletContext 보관소에 저장된 값 꺼내기.
        //  => 먼저 ServletContext 객체를 알아낸다.
        ServletContext sc = this.getServletContext();
        out.printf("\nServletContext : aaa=%s", sc.getAttribute("aaa"));
        
        //  Servlet01에서 ServletRequest 보관소에 저장한 값을 꺼낼수 있는가?
        // XX
        out.printf("\nServletRequest : bbb=%s", req.getAttribute("bbb"));

    }
}

/*
    
*/