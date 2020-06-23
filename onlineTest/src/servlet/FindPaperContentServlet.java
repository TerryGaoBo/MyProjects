package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: GAOBO
 * Date: 2020-06-08
 * Time: 17:50
 */
@WebServlet("/findPaperContentServlet")
public class FindPaperContentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=utf-8");
        Object itemList = req.getSession().getAttribute("itemList");
        ObjectMapper mapper=new ObjectMapper();
        mapper.writeValue(resp.getWriter(),itemList);
    }
}
