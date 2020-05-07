package com.st.servlet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.st.dao.LogDao;
import com.st.dao.UserDao;
import com.st.entity.Log;
import com.st.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by tao on 2017/4/9 0009.
 */
@WebServlet(name = "GetAllLogServlet",urlPatterns = "/admin/allLog")
public class GetAllLogServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Log> list = new ArrayList<>();
        LogDao ld = new LogDao();
        try{
            String findnameortime = request.getParameter("findnameortime");
            System.out.println("findnameortime:" + findnameortime);

            if(findnameortime!= null && !findnameortime.equals("")) {
                findnameortime = findnameortime.trim();
                //先判断是不是时间的格式
                if(isLegalDate(findnameortime)) {
                    list = ld.getByWorkdate(findnameortime);
                    System.out.println("list->>>>"+list);
                }else {
                    //按照姓名/工号进行查找
                    int uid = Integer.parseInt(findnameortime);
                    list = ld.getByUid(uid);
                    System.out.println("list->>>>"+list);
                }
            }else {
                list = ld.findAll();
                System.out.println("list:===>"+list);
            }

            PrintWriter out = response.getWriter();
            //将list的数据转换成JSON返回给前台
            JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd";
            //SerializerFeature.WriteDateUseDateFormat用来将日期格式化成yyyy-MM-dd的形式
            String json= JSON.toJSONString(list, SerializerFeature.WriteDateUseDateFormat);
            System.out.println("json=============>"+json);
            out.println(json);
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private static boolean isLegalDate(String sDate) {
        int legalLen = 10;
        if ((sDate == null) || (sDate.length() != legalLen)) {
            return false;
        }
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = formatter.parse(sDate);
            return sDate.equals(formatter.format(date));
        } catch (Exception e) {
            return false;
        }
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
