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
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: GAOBO
 * Date: 2020-06-08
 * Time: 17:16
 */
@WebServlet("/startExamServlet")
public class StartExamServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=utf-8");

        String id = req.getParameter("id");  //获取参数
        int paperId = Integer.parseInt(id);

        PaperDao paperDao = new PaperDao();

        List<Item> itemList = paperDao.queryPaperById(paperId);
        Map<String,Object> map=new HashMap<>();
        System.out.println("itemList： "+itemList);
        if (itemList != null){
            map.put("msg",true);
            req.getSession().setAttribute("paperId",paperId);
        }else{
            map.put("msg",false);
        }
        req.getSession().setAttribute("itemList",itemList);
        ObjectMapper mapper=new ObjectMapper();
        mapper.writeValue(resp.getWriter(),map);
    }
}
