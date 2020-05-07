package com.st.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by tao on 2017/4/9 0009.
 */
//@WebFilter(filterName = "admin/*")
public class AdminLoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        System.out.println("admin=>>>的登录");
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)resp;
        //当前地址
        String currentURL = request.getRequestURI();
        System.out.println("当前地址currentURL:"+currentURL);
        //应用地址
        String ctxPath = request.getContextPath();
        System.out.println("应用地址ctxPath:"+ctxPath);
        //当前地址去除掉应用地址
        String targetURL = currentURL.substring(ctxPath.length());
        System.out.println("当前地址去除掉应用地址targetURL:"+targetURL);
        //登录页面
        String loginPage = "/admin/login.jsp";
        //登录的Servlet对应的url
        String loginServlet = "/admin/login";
        // 获取session,如果session不存在，则返回null
        HttpSession session = request.getSession(false);
        //对当前页面进行判断，如果当前页面不为登录页面
        if(loginPage.equals(targetURL)){
            System.out.println("当前页面是登录页面");
            //这里表示如果当前页面是登陆页面，跳转到登陆页面  把当前页面就放行了
            chain.doFilter(request, response);
            return;
        }else{
            if(loginServlet.equals(targetURL)){
                //Servlet验证,
                chain.doFilter(request, response);
                return;
            }else{
                //在不为登陆页面时，再进行判断，如果不是登陆页面也没有session则跳转到登录页面，
                if(session == null || session.getAttribute("adminId") == null ){
                    System.out.println("管理员您还没有登录！");
                    response.sendRedirect(ctxPath+loginPage);
                    System.out.println("redirect:"+ctxPath+loginPage);
                    return;
                }else{
                    //这里表示正确，会去寻找下一个链，如果不存在，则进行正常的页面跳转
                    chain.doFilter(request, response);
                    return;
                }
            }
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
