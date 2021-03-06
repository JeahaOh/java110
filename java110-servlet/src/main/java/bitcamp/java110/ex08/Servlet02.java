/*  리다이렉트
    서버가 콘텐트를 보내지 않고, 즉 클라이언트가 뭔가를 출력하지 않고,
    즉시 지정된 URL을 요청하도록 만드는 기술.

 */

package bitcamp.java110.ex08;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ex08/servlet02")
public class Servlet02 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    public void doGet(
            HttpServletRequest req,
            HttpServletResponse res)
                    throws ServletException, IOException {
        //  http://localhost:8888/ex08/servlet02

        res.setContentType("text/html;charset=UTF-8");
        PrintWriter out = res.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<title> - ex08 - </title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>servlet02 실행</h1>");
        out.println("</body>");
        out.println("</html>");
        
        //  sendRedirect()를 호출하기 전에 버퍼의 내용을 웹 브라우져로 출력한다면.
        //  sendRedirect()는 무시됨.
        
//        out.flush();
        
        //  클라이언트에게 즉시 다음 URL로 요청하게 명령한다.
        res.sendRedirect("http://google.com");
        
        /*  질문 1) 그럼 지금까지 출력한 내용은 어떻게 되나?
            =>out.println() 등을 사용하여 출력한 내용은
              실제 내부 버퍼에 보관되어 있음.
              즉, 아직 출력하지 않은 상태인것.
            =>sendRedirect()를 호출하면 이 버퍼에 보관된 내용을 버려 버림.
            =>응답할 때도 웹 브라우저에게 message-body를 출력하지 않는다.
            
            질문 2) 버퍼가 꽉 차면 어떻게 되나>
            => 자동으로 출력되버림
            => 출력해버렸기 때문에 sendRedirect()는 무시된다.
            
        
        
        
        
        */
    }
}

/*
응답 상태 코드가 200이 아님.
웹브라우져가 다시 요청할 URL 정보가 뜸.
        
==============================
클라이언트가 출력할 내용을 보내지 않는다. 즉, 
 */