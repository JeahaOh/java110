package bitcamp.java110.cms.servlet.teacher;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bitcamp.java110.cms.domain.Teacher;
import bitcamp.java110.cms.service.TeacherService;

@WebServlet("/teacher/list")
public class TeacherListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doGet(
            HttpServletRequest request, 
            HttpServletResponse response) 
            throws ServletException, IOException {
        
        int pageNo = 1;
        int pageSize = 5;
        
        if(request.getParameter("pageNo") != null) {
            pageNo = Integer.parseInt(request.getParameter("pageNo"));
            if(pageNo < 1) {
                pageNo = 1;
            }
        }
        
        if(request.getParameter("pageSize") != null) {
            pageSize = Integer.parseInt(request.getParameter("pageSize"));
            if(pageSize <= 5 || pageSize >= 10) {
                pageSize = 5;
            }
        }
        
        TeacherService teacherService = (TeacherService)this.getServletContext()
                .getAttribute("teacherService");
        
        List<Teacher> list = teacherService.list(pageNo, pageSize);
        request.setAttribute("list", list);
        
        response.setContentType("text/html;charset=UTF-8");
        
        RequestDispatcher rd = request.getRequestDispatcher(
                "/teacher/list.jsp");
        rd.include(request, response);
    }
}
