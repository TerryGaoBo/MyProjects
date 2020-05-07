package com.st.servlet;

import com.st.dao.LogDao;
import com.st.entity.Log;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: GAOBO
 * Date: 2020-01-11
 * Time: 14:36
 */

@WebServlet("/admin/changeAdminLog")
public class ChangeAdminLogServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try{
            //获取参数
            int id = Integer.parseInt(request.getParameter("hiddenID"));
            System.out.println("id--->"+id);
            //1、判断该uid是否存在
            LogDao ld = new LogDao();
            Log lid = ld.get(id);
            if(lid == null) {
                System.out.println("没有此id对应的LOG");
                return;
            }
            int uid = Integer.parseInt(request.getParameter("uid"));
            Date workdate = Date.valueOf(request.getParameter("workdate"));
            String describe = request.getParameter("describe");
            int worktime = Integer.parseInt(request.getParameter("worktime"));
            String difficulty = request.getParameter("difficulty");
            String remark =  request.getParameter("remark");
            String status = "未审核";
            //生成日志对象
            Log log = new Log(id,uid,workdate,describe,worktime,difficulty,remark,status);
            //更新数据
            boolean result = ld.update(log);
            PrintWriter out = response.getWriter();
            boolean returnMsg = false;
            if (result){
                returnMsg = true;
            }
            out.println(returnMsg);
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
