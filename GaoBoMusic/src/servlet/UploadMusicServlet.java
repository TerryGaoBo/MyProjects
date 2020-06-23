package servlet;

import entity.User;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: GAOBO
 * Date: 2020-05-27
 * Time: 15:41
 */
@WebServlet("/upload")
//@MultipartConfig
public class UploadMusicServlet extends HttpServlet {

    private final String SAVEPATH="E://Javaproject//GaoBoMusic//web//music//";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            request.setAttribute("Msg", "请登录后再进行上传");
            request.getRequestDispatcher("uploadmusic.jsp").forward(request,
                    response);
        } else {

            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            List<FileItem> items = null;

            try {
                items = upload.parseRequest(request);
            } catch (FileUploadException e) {
                e.printStackTrace();
                return;
            }


            System.out.println("items:"+items );
            FileItem item = items.get(0);
            System.out.println("item： "+item);

            String fileName = item.getName();

            System.out.println("fileName"+fileName);
            request.getSession().setAttribute("fileName", fileName);

            try {
                item.write(new File(SAVEPATH, fileName));
            } catch (Exception e) {
                e.printStackTrace();
            }
            response.sendRedirect("uploadsucess.html");
        }
    }
}
