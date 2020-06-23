package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.PaperDao;
import entity.ScoreTable;

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
 * Date: 2020-06-11
 * Time: 16:49
 */
@WebServlet("/findScoreServlet")
public class FindScoreServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=utf-8");
        Integer paperId  =  (Integer)req.getSession().getAttribute("paperId");
        PaperDao paperDao = new PaperDao();
        System.out.println("paperDao: "+paperDao);
        try {
            List<ScoreTable> scoreTableList = paperDao.queryScore();
            if(scoreTableList != null) {
                ObjectMapper mapper = new ObjectMapper();
                mapper.writeValue(resp.getWriter(),scoreTableList);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
