package servlet;

import service.UserService;
import service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/deleteServlet")
public class DeleteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String id = request.getParameter("id");
        UserService userService=new UserServiceImpl();
        int i= 0;
        try {
            i = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        int result = userService.Delete(i);
        if(result==1){
            response.sendRedirect("http://localhost/UserProject/list.html");
        }else{
            response.sendRedirect("http://localhost/UserProject/list.html");
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
