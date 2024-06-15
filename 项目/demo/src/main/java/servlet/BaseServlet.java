package servlet;

import Utils.MyException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 所有的servlet都要继承此类
 */
public class BaseServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            //设置字符集编码
            req.setCharacterEncoding("utf-8");
            resp.setCharacterEncoding("utf-8");
            resp.setContentType("text/html;charset=utf-8");
            //获取请求路径中的方法名
            String m=req.getParameter("method");
            //获取当前类
            Class<?extends BaseServlet> clazz=this.getClass();
            //获取要执行的方法
            Method method=clazz.getDeclaredMethod(m,HttpServletRequest.class,HttpServletResponse.class);
            method.setAccessible(true);
            method.invoke(this,req,resp);
        } catch (InvocationTargetException e) {
            if(e.getTargetException() instanceof MyException){
                //自定义异常
                req.setAttribute("msg",e.getTargetException().getMessage());
            }else {
                req.setAttribute("msg","网络异常");
                e.printStackTrace();
            }

            req.getRequestDispatcher("error.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("baseServlet异常处理");
            req.setAttribute("msg","异常");
            req.getRequestDispatcher("error.jsp").forward(req,resp);
        }
    }
}

