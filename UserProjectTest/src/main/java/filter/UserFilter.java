package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class UserFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        //强转
        HttpServletRequest request= (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        //获取请求路径
        String uri = request.getRequestURI();
        if(uri.contains("/login.html") || uri.contains("/checkCode") || uri.contains("/loginServlet")||uri.contains("/css/")||uri.contains("/fonts/")||uri.contains("/js/")||uri.contains("/images/")){
            chain.doFilter(req,resp);
        }else{
            Object user = request.getSession().getAttribute("user");
            if (user!=null){
                //已经登录了
                chain.doFilter(req,resp);
            }else{
                //没有登录
                response.sendRedirect("/UserProject/login.html");
            }
        }

    }

    public void init(FilterConfig config) throws ServletException {

    }

}
