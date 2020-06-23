package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.UserDao;
import entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.HttpCookie;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: GAOBO
 * Date: 2020-06-03
 * Time: 15:00
 */
@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("comeheer");
        resp.setContentType("application/json;charset=utf-8");   //设置响应数据的数据格式和编码格式

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User loginUser =new User();  //创建一个数据库实体类对象
        loginUser.setUsername(username);
        loginUser.setPassword(password);

        UserDao userDao =new UserDao();   //创建service层的对象

        Map<String ,Object> return_map = new HashMap<>();   //创建一个map集合，存放返回到客户端的数据

        User user = userDao.login(loginUser);   //调用service层的登方法，判断是否登录成功
        if (user!=null){
            //说明数据库中有，显示登录成功，把登录信息存入session
            req.getSession().setAttribute("user",user);
            //返回给登录页面json数据
            return_map.put("msg",true);
        }else{
            System.out.println("账号或密码错误");
            //账号或密码错误
            return_map.put("msg",false);
        }

        ObjectMapper mapper = new ObjectMapper();   //利用Jackson将map转化为json对象

        mapper.writeValue(resp.getWriter(),return_map);
    }
}
