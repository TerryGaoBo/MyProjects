package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.PaperDao;
import entity.Item;
import entity.Paper;
import entity.PaperItem;
import entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: GAOBO
 * Date: 2020-06-03
 * Time: 15:08
 */
@WebServlet("/recordPaperServlet")
public class RecordPaperServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=utf-8");
        String[] values = req.getParameterValues("id[]");
        System.out.println("录试卷："+Arrays.toString(values));
        Map<String,Object> map=new HashMap<>();

        if(values == null) {
            map.put("msg",false);
        }else {
            User user = (User) req.getSession().getAttribute("user");
            Paper paper = new Paper();
            paper.setUserId(user.getId());
            Date date= new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String createTime = simpleDateFormat.format(date);
            paper.setCreateTime(createTime);

            PaperDao paperDao = new PaperDao();
            for (int i = 0; i < values.length; i++) {
                PaperItem paperItem = new PaperItem();
                Item item = paperDao.queryItemById(Integer.valueOf(values[i]));
                paperItem.setItemId(item.getId());
                paper.getPaperItemList().add(paperItem);
            }
            boolean flg = paperDao.recordPaper(paper);
            if(flg) {
                System.out.println("组卷成功！");
                map.put("msg",true);
            }else {
                map.put("msg",false);
            }
            req.getSession().setAttribute("paper",paper);
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(resp.getWriter(),map);
    }
}
