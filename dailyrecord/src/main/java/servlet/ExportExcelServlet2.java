package servlet;

import dao.LogDao;
import entity.Log;
import util.ExcelUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: GAOBO
 * Date: 2020-01-05
 * Time: 19:48
 */
@WebServlet("/exportExcel2")
public class ExportExcelServlet2 extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        System.out.println("ExportExcelServlet2---->");
        try {

            int uid = (Integer)request.getSession().getAttribute("adminId");
            LogDao ld = new LogDao();

            String[] title = {"ID", "日期", "内容", "时长", "难度", "备注"};
            List<Log> list = ld.getByUid(uid);
            System.out.println("ExportExcelServlet2.list-->"+list);
            String filename = "attendSelf.xlsx";
            String sheetName = "sheet1";
            String[][] content = new String[list.size()][6];
            for (int i = 0; i < list.size(); i++) {
                content[i][0] = String.valueOf(list.get(i).getUid());
                content[i][1] = String.valueOf(list.get(i).getWorkdate());
                content[i][2] = list.get(i).getDesribe();
                content[i][3] = String.valueOf(list.get(i).getWorktime());
                content[i][4] = list.get(i).getDifficulty();
                content[i][5] = list.get(i).getRemark();
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
}
