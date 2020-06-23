package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.PaperDao;
import entity.Item;
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
 * Date: 2020-06-04
 * Time: 15:30
 */
@WebServlet("/addItemServlet")
public class addItemServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //设置编码
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=utf-8");
        //获取数据
        String title = req.getParameter("title");
        String aSelect = req.getParameter("aSelect");
        String bSelect = req.getParameter("bSelect");
        String cSelect = req.getParameter("cSelect");
        String dSelect = req.getParameter("dSelect");
        String scoreDou = req.getParameter("score");
        double score = Double.valueOf(scoreDou);
        String answer = req.getParameter("answer");
        Item item = new Item();
        item.setTitle(title);
        item.setContentA(aSelect);
        item.setContentB(bSelect);
        item.setContentC(cSelect);
        item.setContentD(dSelect);
        item.setScore(score);
        item.setAnswer(answer);

        Map<String,Object> map = new HashMap<>();
        if(!item.getTitle().equals("") && !item.getContentA().equals("")
                &&!item.getContentB().equals("")&&!item.getContentC().equals("")
                && !item.getContentD().equals("")&&item.getScore() != 0) {
            //每个数据全部已经输入
            PaperDao paperDao = new PaperDao();
            int result = paperDao.insertItem(item);
            if (result==1) {
                //说明成功了
                map.put("msg", true);
            } else {
                map.put("msg",false);
            }
        }else{
            map.put("msg",false);
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(resp.getWriter(),map);

    }

}
