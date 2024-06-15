package servlet;

import Utils.MD5;
import Utils.PageTool;
import Utils.PaginationUtils;
import Utils.ResBean;
import com.google.gson.Gson;
import entity.UserDb;
import org.apache.commons.beanutils.BeanUtils;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * 用户
 */
@WebServlet("/user")
public class UserServlet extends BaseServlet {

    private static final long serialVersionUID = 1L;
    private UserService userService = new UserService();

    /**
     * 用户列表  分页
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void list(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException {
        String currentPage= req.getParameter("pageNum");
        String pageSize= req.getParameter("pageSize");
        PageTool<UserDb> pageTools= userService.list(currentPage,pageSize);
        //生成前端分页按钮
        String pagation= PaginationUtils.getPagation(pageTools.getTotalCount(),
                pageTools.getCurrentPage(),
                pageTools.getPageSize(),
                "user?method=list");
        req.setAttribute("pagation",pagation);
        req.setAttribute("uList", pageTools.getRows());
        req.getRequestDispatcher("admin/admin_user.jsp").forward(req,resp);
    }

    /**
     *添加用户
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void addUser(HttpServletRequest req, HttpServletResponse resp)  throws ServletException,
            IOException,IllegalAccessException, InvocationTargetException {

        UserDb userDb = new UserDb();
        BeanUtils.populate(userDb,req.getParameterMap());
        userDb.setTimes(0);
        userDb.setPassword(MD5.valueOf(userDb.getPassword()));
        userService.addUser(userDb);
        resp.sendRedirect("user?method=list");
    }

    /**
     * 管理员修改用户登录信息
     * @param req
     * @param resp
     */
    public void updateUser(HttpServletRequest req, HttpServletResponse resp)  throws Exception {
        UserDb userDb = new UserDb();
        BeanUtils.populate(userDb,req.getParameterMap());
        userService.updateUser(userDb);
        resp.sendRedirect("user?method=list");
    }

    /**
     * 删除用户信息
     * @param req
     * @param resp
     * @throws Exception
     */
    public void delUser(HttpServletRequest req, HttpServletResponse resp)  throws Exception {
       String uid= req.getParameter("uid");
       userService.delUser(Integer.parseInt(uid));
        resp.sendRedirect("user?method=list");
    }
/**
 * 异步检验用户账号
 */
    public void checkUser(HttpServletRequest req, HttpServletResponse resp)  throws IOException {
        String account= req.getParameter("account");
        UserDb userDb=new UserDb();
        userDb.setAccount(account);
        List<UserDb> list= userService.getList(userDb);
        ResBean resBean=new ResBean();
        if(list!=null&&list.size()>0){
            resBean.setCode(400);
            resBean.setMsg("账号被占用");
        }else {
            resBean.setCode(200);
            resBean.setMsg("账号可用");
        }
        Gson gson=new Gson();
        String json=gson.toJson(resBean);
        resp.getWriter().print(json);

    }
}
