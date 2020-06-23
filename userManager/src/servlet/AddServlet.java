package servlet;

import entity.User;
import service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/addServlet")
public class AddServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置编码
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");
        //获取数据
        String name = request.getParameter("name");
        String gender = request.getParameter("gender");
        String ageString = request.getParameter("age");
        int age = Integer.parseInt(ageString);
        String address = request.getParameter("address");
        String qq = request.getParameter("qq");
        String email = request.getParameter("email");

        User addUser =new User();
        addUser.setName(name);
        addUser.setGender(gender);
        addUser.setAge(age);
        addUser.setAddress(address);
        addUser.setQq(qq);
        addUser.setEmail(email);

        UserService userService=new UserService();
        //创建一个map集合
        Map<String,Object>  map = new HashMap<>();
        System.out.println("===========addServlet=========== " + addUser);
        if(!addUser.getName().equals("")&& !addUser.getQq().equals("")&&!addUser.getEmail().equals("")&&addUser.getAge()!=0){
            //说明用户填写了数据
            int result = userService.add(addUser);
            if (result==1) {
                //说明成功了
                map.put("msg", true);
            } else {
                map.put("msg",false);
            }
            //将数据存入map集合
        }else{
            map.put("msg",false);
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getWriter(),map);
    }
}
