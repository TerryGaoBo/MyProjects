package com.st.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by tao on 2017/4/6 0006.
 */
//@WebFilter("*.jsp")
public class LoginFilter implements Filter {
    public void destroy() {
        System.out.println("过滤器销毁..........");
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        System.out.println("=========执行过滤操作=========");
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)resp;
        //获得客户端访问网站的相对路径
        String currentURL = request.getRequestURI();
        System.out.println("currentURL:"+currentURL);
        //ctxPath:得到项目的名字，如果项目为根目录，则得到一个""，即空的字条串
        //例如：http://localhost:8080/blue/login.jsp--->/blue
        String ctxPath = request.getContextPath();
        System.out.println("ctxPath:"+ctxPath);
        //除掉项目名称时访问页面当前路径
        String targetURL = currentURL.substring(ctxPath.length());
        System.out.println("targetURL:"+targetURL);
        //登录页面
        String loginPage = "/login.jsp";
        //登录的Servlet对应的url
        String loginServlet = "/login";
        HttpSession session = request.getSession(false);
        //对当前页面进行判断，如果当前页面为登录页面
        if(loginPage.equals(targetURL)){
            //这里表示如果当前页面是登陆页面，跳转到登陆页面
            System.out.println("loginPage.equals(targetURL)");
            chain.doFilter(request, response);//放行
            return;
        }else{
            if(session == null || session.getAttribute("uId") == null ){
                System.out.println("您还没有登录");
                response.sendRedirect(ctxPath+loginPage);
                System.out.println("redirect:"+ctxPath+loginPage);
                return;
            }else{
                System.out.println("session != null");
                chain.doFilter(request, response);//放行
                return;
            }
            /*if(loginServlet.equals(targetURL)){
                //Servlet验证
                System.out.println("loginServlet.equals(targetURL)");
                chain.doFilter(request, response);//放行
                return;
            }else{

                if(session == null || session.getAttribute("uId") == null ){
                    System.out.println("您还没有登录");
                    response.sendRedirect(ctxPath+loginPage);
                    System.out.println("redirect:"+ctxPath+loginPage);
                    return;
                }else{
                    System.out.println("session != null");
                    chain.doFilter(request, response);//放行
                    return;
                }
            }*/
        }
    }

    public void init(FilterConfig config) throws ServletException {
        System.out.println("过滤器初始化.........");
    }

}
