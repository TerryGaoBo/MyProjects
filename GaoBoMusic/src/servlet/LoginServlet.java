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
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: GAOBO
 * Date: 2020-05-29
 * Time: 15:58
 */
@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=utf-8");
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        System.out.println("username："+username);
        System.out.println("password："+password);

        UserDao dao = new UserDao();

        Map<String ,Object> return_map = new HashMap<>();

        try {
            User user = dao.selectUser(username);
            if(user!=null && user.getPassword().equals(password)) {
                req.getSession().setAttribute("user", user);//绑定数据
                return_map.put("msg",true);
            }else {
                System.out.println("密码错误！");
                return_map.put("msg",true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ObjectMapper mapper = new ObjectMapper();   //利用Jackson将map转化为json对象
        mapper.writeValue(resp.getWriter(),return_map);
    }
}
