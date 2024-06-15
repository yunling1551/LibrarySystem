package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        //Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //强转请求和相应
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        //获取请求路径
        String uri = req.getRequestURI();
        //路径判断 部分内容不拦截
        if (uri.contains("/login") || uri.contains("/static")) {
            //放行
            chain.doFilter(req, res);
        }else{
            //验证是否登录
            Object userDb= req.getSession().getAttribute("userDb");
            if(userDb!=null){
                //用户已经登陆
                chain.doFilter(req, res);
            }else{
                //未登录
                res.sendRedirect("login.jsp");
            }
        }

    }

    @Override
    public void destroy() {
        //Filter.super.destroy();
    }
}
