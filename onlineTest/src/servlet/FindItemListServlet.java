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
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: GAOBO
 * Date: 2020-06-03
 * Time: 15:53
 */
@WebServlet("/findItemListServlet")
public class FindItemListServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=utf-8");
        System.out.println("测试查找函数");
        String str = req.getParameter("title");
        try {
            PaperDao paperDao = new PaperDao();
            List<Item> itemList = null;
            if(str != null) {
                itemList = paperDao.findItemByTitle(str);//关键字查询
            }else {
                itemList = paperDao.findItem();
            }
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(resp.getWriter(),itemList);
        }catch (Exception e) {
            e.printStackTrace();
        }

    }
}
