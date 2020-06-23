package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.PaperDao;
import entity.Item;

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
 * Date: 2020-06-08
 * Time: 15:35
 */
@WebServlet("/findItemServlet")
public class FindItemServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=utf-8");

        String id = req.getParameter("id");  //获取参数
        int i = Integer.parseInt(id);

        PaperDao paperDao = new PaperDao();
        Item item = paperDao.queryItemById(i);
        req.getSession().setAttribute("item",item);

        Map<String,Object> map=new HashMap<>();
        if (item != null){
            map.put("msg",true);
        }else{
            map.put("msg",false);
        }
        ObjectMapper mapper=new ObjectMapper();
        mapper.writeValue(resp.getWriter(),map);

        //跳转到update.html
        //resp.sendRedirect("/update.html");
    }
}
