package servlet;

import service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/deleteSelectedServlet")
public class DeleteSelectedServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=utf-8");
        String[] values = req.getParameterValues("id[]");
        System.out.println("deleteSelectedServlet："+Arrays.toString(values));
        //删除
        int sum=0;
        UserService userService=new UserService();
        Map<String,Object> map=new HashMap<>();
        for(int i=0;i<values.length;i++){
            int j = Integer.parseInt(values[i]);
            //调用Service层方法删除
            int delete = userService.delete(j);
            sum=sum+delete;
        }
        System.out.println("sum: "+sum);
        //sum==values.length 说明选中的所有元素已经全部删除了
        if(sum==values.length){
            //证明删除成功
            map.put("msg",true);
        }else {
            map.put("msg",false);
        }
        //将map转化为json
        ObjectMapper mapper=new ObjectMapper();
        mapper.writeValue(resp.getWriter(),map);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
