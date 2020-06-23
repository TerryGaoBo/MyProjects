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
 * Date: 2020-06-03
 * Time: 15:05
 */
@WebServlet("/updateItemServlet")
public class UpdateItemServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=utf-8");

        Item item = (Item) req.getSession().getAttribute("item");

        String title = req.getParameter("title");
        String aSelect = req.getParameter("aSelect");
        String bSelect = req.getParameter("bSelect");
        String cSelect = req.getParameter("cSelect");
        String dSelect = req.getParameter("dSelect");
        String scoreDou = req.getParameter("score");
        String answer = req.getParameter("answer");
        double score = Double.valueOf(scoreDou);

        Item updateItem = new Item();
        updateItem.setId(item.getId());
        updateItem.setTitle(title);
        updateItem.setContentA(aSelect);
        updateItem.setContentB(bSelect);
        updateItem.setContentC(cSelect);
        updateItem.setContentD(dSelect);
        updateItem.setScore(score);
        updateItem.setAnswer(answer);
        PaperDao paperDao = new PaperDao();
        int ret = paperDao.updateItem(updateItem);
        Map<String,Object> map=new HashMap<>();
        if (ret==1){
            map.put("msg",true);
        }else{
            map.put("msg",false);
        }
        ObjectMapper mapper=new ObjectMapper();
        mapper.writeValue(resp.getWriter(),map);
    }
}
