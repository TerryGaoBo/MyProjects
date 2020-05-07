package com.st.servlet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.st.dao.LogDao;
import com.st.entity.Log;
import com.st.util.ExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

/**
 * Created by tao on 2017/4/9 0009.
 */
@WebServlet("/exportExcel")
public class ExportExcelServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("ExportExcelServlet---->");
        try {

            LogDao ld = new LogDao();

            String[] title = {"ID", "日期", "内容", "时长", "难度", "备注", "状态","工号",};
            List<Log> list = ld.findAll();
            System.out.println("ExportExcelServlet2.list-->"+list);
            String filename = "admin_attend.xlsx";
            String sheetName = "sheet1";
            String[][] content = new String[list.size()][8];
            for (int i = 0; i < list.size(); i++) {
                content[i][0] = String.valueOf(list.get(i).getId());
                content[i][1] = String.valueOf(list.get(i).getWorkdate());
                content[i][2] = list.get(i).getDesribe();
                content[i][3] = String.valueOf(list.get(i).getWorktime());
                content[i][4] = list.get(i).getDifficulty();
                content[i][5] = list.get(i).getRemark();
                content[i][6] = list.get(i).getStatus();
                content[i][7] = String.valueOf(list.get(i).getUid());
            }
            System.out.println("===================");
            XSSFWorkbook wb = ExcelUtil.getXSSFWorkbook(sheetName, title, content);
            // 响应到客户端
            this.setResponseHeader(response, filename);
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
        }catch (Throwable e){
            e.printStackTrace();
        }
    }

    public void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            try {
                fileName = new String(fileName.getBytes(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            response.setContentType("application/vnd.ms-excel;charset=GBK");
            response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8") );
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /*protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            System.out.println("uid:"+request.getParameter("uid"));
            String json = null;
            int uid = Integer.parseInt(request.getParameter("uid"));
            System.out.println("uid:"+uid);
            LogDao ld = new LogDao();
            String workdate = request.getParameter("workdate");
            System.out.println("workdate:" + workdate);

            StringBuilder ret = new StringBuilder();
            ret.append("ID    ").append("日期    ").append("内容    ").append("时长    ").append("难度    ")
                    .append("备注    ").append("状态    ").append("\n");
            if(workdate != null && !workdate.equals("")) {
                List<Log> list = ld.getByWorkdateAndUid(workdate,uid);
                PrintWriter out = response.getWriter();
                //将list的数据转换成JSON返回给前台
                JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd";
                //SerializerFeature.WriteDateUseDateFormat用来将日期格式化成yyyy-MM-dd的形式
                json = JSON.toJSONString(list, SerializerFeature.WriteDateUseDateFormat);
                System.out.println("json" + json);
                out.println(json);
                out.close();

                for (int i = 0; i < list.size(); i++) {
                    ret.append(String.valueOf(list.get(i).getId()));
                    ret.append("    ");
                    ret.append(String.valueOf(list.get(i).getWorkdate()));
                    ret.append("    ");
                    ret.append(list.get(i).getDesribe());
                    ret.append("    ");
                    ret.append(list.get(i).getWorktime());
                    ret.append("    ");
                    ret.append(list.get(i).getDifficulty());
                    ret.append("    ");
                    ret.append(list.get(i).getRemark());
                    ret.append("    ");
                    ret.append(list.get(i).getStatus());
                    ret.append("\n");
                }

            }else {
                List<Log> list = ld.getByUid(uid);
                PrintWriter out = response.getWriter();
                //将list的数据转换成JSON返回给前台
                JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd";
                //SerializerFeature.WriteDateUseDateFormat用来将日期格式化成yyyy-MM-dd的形式
                json = JSON.toJSONString(list, SerializerFeature.WriteDateUseDateFormat);
                System.out.println("json" + json);
               // out.println(json);
                //out.close();
                for (int i = 0; i < list.size(); i++) {
                    ret.append(String.valueOf(list.get(i).getId()));
                    ret.append("    ");
                    ret.append(String.valueOf(list.get(i).getWorkdate()));
                    ret.append("    ");
                    ret.append(list.get(i).getDesribe());
                    ret.append("    ");
                    ret.append(list.get(i).getWorktime());
                    ret.append("    ");
                    ret.append(list.get(i).getDifficulty());
                    ret.append("    ");
                    ret.append(list.get(i).getRemark());
                    ret.append("    ");
                    ret.append(list.get(i).getStatus());
                    ret.append("\n");
                }
            }
            System.out.println("ret"+ret);
            File file = new File("E:\\LOG.txt");
            if(!file.exists()){
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);
            BufferedWriter out2 = new BufferedWriter(fw);
            String ret2 = ret.toString();
            out2.write(ret2);
            out2.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("=================");
        doPost(request,response);
    }*/
}
