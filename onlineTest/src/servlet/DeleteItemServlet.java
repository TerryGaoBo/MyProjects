package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.PaperDao;

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
 * Time: 15:04
 */
@WebServlet("/deleteItemServlet")
public class DeleteItemServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String strId = req.getParameter("id");
        System.out.println("DeleteServlet："+strId);

        int id = Integer.parseInt(strId);
        System.out.println("id:"+ id);

        PaperDao paperDao = new PaperDao();
        int delete  = paperDao.deleteItemId(id);
        Map<String,Object> map=new HashMap<>();

        if(delete == 1) {
            map.put("msg",true);
        }else {
            map.put("msg",false);
        }
        //将map转化为json
        ObjectMapper mapper=new ObjectMapper();
        mapper.writeValue(resp.getWriter(),map);
    }
}
