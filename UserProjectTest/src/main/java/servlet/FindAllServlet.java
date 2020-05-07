package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/findAllServlet")
public class FindAllServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //调用service层的方法，查询数据
      /*  response.setContentType("application/json;charset=utf-8");
        UserService userService=new UserServiceImpl();
        List<User> users = userService.findAll();
        ObjectMapper mapper =new ObjectMapper();
        mapper.writeValue(response.getWriter(),users);*/
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
