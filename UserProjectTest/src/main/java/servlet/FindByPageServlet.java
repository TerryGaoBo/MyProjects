package servlet;

import domain.PageBean;
import domain.User;
import service.UserService;
import service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet("/findByPageServlet")
public class FindByPageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        String currentPage = request.getParameter("currentPage");
        String rows = request.getParameter("rows");
        List<Object> list=new ArrayList<>();
        Map<String, String[]> map = request.getParameterMap();
        //强制类型转换
        int current = Integer.parseInt(currentPage);
        int row = Integer.parseInt(rows);
        //创建service层的对象
        UserService userService=new UserServiceImpl();
        PageBean<User> pageBean = userService.FindAllByPage(current, row,map);
        ObjectMapper mapper=new ObjectMapper();
        mapper.writeValue(response.getWriter(),pageBean);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
