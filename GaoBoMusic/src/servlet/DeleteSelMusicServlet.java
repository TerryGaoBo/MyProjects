package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.MusicDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: GAOBO
 * Date: 2020-06-01
 * Time: 13:52
 */
@WebServlet("/deleteSelMusicServlet")
public class DeleteSelMusicServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=utf-8");

        String[] values = req.getParameterValues("id[]");
        System.out.println("deleteSelectedServlet："+Arrays.toString(values));
        //删除
        int sum=0;
        Map<String,Object> map=new HashMap<>();

        MusicDao musicDao = new MusicDao();
        for(int i=0;i<values.length;i++){
            int j = Integer.parseInt(values[i]);
            System.out.println("j :"+j);
            //调用Service层方法删除
            int delete = musicDao.deleteMusicById(j);
            sum=sum+delete;
        }
        System.out.println("sum: "+sum);
        //sum==values.length 说明选中的所有元素已经全部删除了
        if(sum==values.length){
            //证明删除成功
            map.put("msg",true);
        }else {
            map.put("msg",false);
        }
        //将map转化为json
        ObjectMapper mapper=new ObjectMapper();
        mapper.writeValue(resp.getWriter(),map);
    }
}
