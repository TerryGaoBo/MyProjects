package servlet;

import  dao.LogDao;
import  entity.Log;
import util.ExcelUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by tao on 2017/4/9 0009.
 */
@WebServlet("/exportExcel")
public class ExportExcelServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        System.out.println("ExportExcelServlet---->");
        try {

            LogDao ld = new LogDao();

            String[] title = {"ID", "日期", "内容", "时长", "难度", "备注","工号",};
            List<Log> list = ld.findAll();
            System.out.println("ExportExcelServlet2.list-->"+list);
            String filename = "attendAll.xlsx";
            String sheetName = "sheet1";
            String[][] content = new String[list.size()][7];
            for (int i = 0; i < list.size(); i++) {
                content[i][0] = String.valueOf(list.get(i).getId());
                content[i][1] = String.valueOf(list.get(i).getWorkdate());
                content[i][2] = list.get(i).getDesribe();
                content[i][3] = String.valueOf(list.get(i).getWorktime());
                content[i][4] = list.get(i).getDifficulty();
                content[i][5] = list.get(i).getRemark();
                content[i][6] = String.valueOf(list.get(i).getUid());
            }
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
}
