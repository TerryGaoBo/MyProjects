package servlet;

import domain.User;
import service.UserService;
import service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");   //设置响应数据的数据格式和编码格式
        Map<String, String[]> map = request.getParameterMap();  //获取数据的map集合
        User loginUser =new User();  //创建一个数据库实体类对象
        try {
            BeanUtils.populate(loginUser,map);   // 将map集合转化为实体类
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        UserService userService=new UserServiceImpl();   //创建service层的对象
        String checkcode_server = (String) request.getSession().getAttribute("CHECKCODE_SERVER");   //从session中获取生成的验证码
        request.getSession().removeAttribute("CHECKCODE_SERVER");
        Map<String ,Object> return_map=new HashMap<>();   //创建一个map集合，存放返回到客户端的数据
        ObjectMapper mapper=new ObjectMapper();   //利用Jackson将map转化为json对象
        if(loginUser.getCode().equalsIgnoreCase(checkcode_server)){   //比较生成的验证码和输入的验证码是否一致
            //验证码正确
            return_map.put("code_result",true);    //将添加键值对到map集合，说明验证码正确
            User user = userService.login(loginUser);   //调用service层的登方法，判断是否登录成功
            if (user!=null){
                //说明数据库中有，显示登录成功，把登录信息存入session
                request.getSession().setAttribute("user",user);
                //返回给登录页面json数据
 /*               System.out.println(user);//执行了这句*/
                return_map.put("msg",true);
            }
            else{
                //账号或密码错误
                return_map.put("msg",false);
            }
        }else{
            return_map.put("code_result",false);
        }
        mapper.writeValue(response.getWriter(),return_map);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
