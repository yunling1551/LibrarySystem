package servlet;

import Utils.MD5;
import entity.UserDb;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends BaseServlet{
    private static final long serialVersionUID = 1L;

    private UserService userService = new UserService();

    /**
     * 用户登录
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        String account=request.getParameter("account");
        String password=request.getParameter("password");
        HttpSession session=request.getSession();
        UserDb userDb=userService.login(account, MD5.valueOf(password));
        if(userDb==null){
            //账号密码错误
            request.setAttribute("msg","账号密码错误");
            request.getRequestDispatcher("login.jsp").forward(request,response);
        }else{
            //登陆成功
            session.setAttribute("userDb",userDb);
            request.getRequestDispatcher("index.jsp").forward(request,response);
        }
    }
}
