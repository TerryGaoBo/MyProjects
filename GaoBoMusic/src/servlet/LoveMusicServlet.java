package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.MusicDao;
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
 * Date: 2020-06-01
 * Time: 14:56
 */
@WebServlet("/loveMusicServlet")
public class LoveMusicServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=utf-8");

        String strId = req.getParameter("id");
        int musicId = Integer.parseInt(strId);
        System.out.println("musicID: "+musicId);

        User user = (User) req.getSession().getAttribute("user");
        int user_id = user.getId();
        MusicDao musicDao = new MusicDao();

        Map<String,Object> map=new HashMap<>();

        //插入之前需要先查看是否该音乐已经被添加到喜欢列表
        boolean effect = musicDao.findMusicByMusicId(user_id,musicId);
        if(effect) {
            map.put("msg",false);
        }else {
            boolean flg = musicDao.insertLoveMusic(user_id,musicId);
            if(flg) {
                map.put("msg",true);
            }else {
                map.put("msg",false);
            }
        }
        ObjectMapper mapper=new ObjectMapper();
        mapper.writeValue(resp.getWriter(),map);
    }
}
