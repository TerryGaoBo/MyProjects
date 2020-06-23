package servlet;

import entity.User;
import service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/updateServlet")
public class UpdateServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");
        Object us = request.getSession().getAttribute("user");

        User user= (User) us;
        User updateUser =new User();
        String name = request.getParameter("name");
        String gender = request.getParameter("gender");
        String ageString = request.getParameter("age");
        int age = Integer.parseInt(ageString);
        String address = request.getParameter("address");
        String qq = request.getParameter("qq");
        String email = request.getParameter("email");

        //将id赋给updateUser对象
        updateUser.setId(user.getId());
        updateUser.setName(name);
        updateUser.setGender(gender);
        updateUser.setAge(age);
        updateUser.setAddress(address);
        updateUser.setQq(qq);
        updateUser.setEmail(email);

        //调用Service层的方法，更新
        UserService userService=new UserService();
        int i = userService.update(updateUser);
        Map<String,Object> return_map=new HashMap<>();
        if (i==1){
            return_map.put("msg",true);
        }else{
            return_map.put("msg",false);
        }
        ObjectMapper mapper=new ObjectMapper();
        mapper.writeValue(response.getWriter(),return_map);

    }

    /*protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }*/
}
