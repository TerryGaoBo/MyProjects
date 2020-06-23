package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.PaperDao;
import entity.Item;
import entity.Paper;
import entity.PaperItem;

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
 * Date: 2020-06-04
 * Time: 13:25
 */
@WebServlet("/findPaperServlet")
public class FindPaperServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=utf-8");
        String str = req.getParameter("paperId");
        PaperDao paperDao = new PaperDao();

        List<Paper> papers = null;

        if(str != null) {
            papers = paperDao.findPaperById(Integer.parseInt(str));//关键字查询
        }else {
            papers = paperDao.findPaper();
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(resp.getWriter(),papers);
    }
}
