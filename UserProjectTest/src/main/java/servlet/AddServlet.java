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

@WebServlet("/addServlet")
public class AddServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置编码
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");
        //获取数据
        Map<String, String[]> map = request.getParameterMap();
        //创建一个空对象
        User addUser =new User();
        //将数据封装成javabean对象
        try {
            BeanUtils.populate(addUser,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        UserService userService=new UserServiceImpl();
        //创建一个map集合
        Map<String,Object>  msg = new HashMap<>();
        if(!addUser.getName().equals("")&& !addUser.getQq().equals("")&&!addUser.getEmail().equals("")&&addUser.getAge()!=0){
            //说明用户填写了数据
            int result = userService.Add(addUser);
            if (result==1) {
                //说明成功了
                msg.put("msg", true);
            }
            else {
                    msg.put("msg",false);
                }
                //将数据存入map集合

        }else{
            msg.put("msg",false);
        }
        ObjectMapper mapper=new ObjectMapper();
        mapper.writeValue(response.getWriter(),msg);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
