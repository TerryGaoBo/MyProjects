package servlet;

import dao.PaperDao;
import entity.Item;
import entity.Paper;
import entity.ScoreTable;
import entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: GAOBO
 * Date: 2020-06-03
 * Time: 15:14
 */
@WebServlet("/commitPaperServlet")
public class CommitPaperServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("提交试卷函数！");
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=utf-8");

        PaperDao paperDao = new PaperDao();
        Map<String, String[]> map  = req.getParameterMap();

        double totalScore = 0;
        for(Map.Entry<String, String[]> entry : map.entrySet()) {
            System.out.print(entry.getKey() +"->" + Arrays.toString(entry.getValue()));
            int itemId = Integer.parseInt(entry.getKey());
            String[] answer = entry.getValue();
            Item item = paperDao.Judgement(itemId,answer[0]);
            if(item != null) {
                totalScore += item.getScore();
            }
        }
        ScoreTable scoreTable = new ScoreTable();
        Integer paperId  =  (Integer)req.getSession().getAttribute("paperId");
        scoreTable.setPaperId(paperId);
        User user = (User) req.getSession().getAttribute("user");
        scoreTable.setUserId(user.getId());
        scoreTable.setTotalScore(totalScore);

        boolean effect = paperDao.commitOverPaper(scoreTable);
        if(effect) {
            System.out.println("提交试卷成功!");
            //跳转到另外的页面，查询本次考试的结果
            resp.sendRedirect("scoreList.html");
        }else {
            System.out.println("提交试卷失败!");
        }
    }
}
