package servlet;

import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
//删除某一行的数据
@WebServlet("/deleteServlet")
public class DeleteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String id = request.getParameter("id");
        System.out.println("DeleteServlet："+id);
        UserService userService=new UserService();
        int i= 0;
        try {
            i = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        int result = userService.delete(i);
        if(result==1){
            response.sendRedirect("/usermanager/list.html");
        }else{
            response.sendRedirect("/usermanager/list.html");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
