package servlet;

import  dao.UserDao;
import  entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

/**
 * Created by tao on 2017/4/9 0009.
 */
@WebServlet("/admin/login")
public class AdminLoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        try{
            //获取参数
            int id = Integer.parseInt(request.getParameter("id"));
            String password = request.getParameter("password");
            //获取项目路径
            String ctxPath = request.getContextPath();
            UserDao ud = new UserDao();
            //通过用户id和password获取用户
            User u = ud.get(id,password);
            //loginPage
            String loginPage = "/admin/login.jsp";
            String indexPage = "/admin/index.jsp";
            PrintWriter out  = response.getWriter();
            if(u == null) {
                //没有找到
                out.println("用户不存在或者密码错误!");
                out.close();
                response.sendRedirect(ctxPath+loginPage);
            } else {
                //找到管理员,设置session
                if(u.getLevel() >= 2){
                    request.getSession().setAttribute("adminId",u.getId());
                    request.getSession().setAttribute("adminName",u.getUsername());
                    //设置成功跳转到主页
                    response.sendRedirect(ctxPath+indexPage);
                }else {
                    out.println("您不是管理员!");
                    out.close();
                }

            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
