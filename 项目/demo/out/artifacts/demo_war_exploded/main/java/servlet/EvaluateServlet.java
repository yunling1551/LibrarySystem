package servlet;

import Utils.PageTool;
import Utils.PaginationUtils;
import entity.EvaluateDb;
import entity.UserDb;
import org.apache.commons.beanutils.BeanUtils;
import service.EvaluateService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

@WebServlet("/problem")
public class EvaluateServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;
    EvaluateService evaluateService = new EvaluateService();

    /**
     * 问题列表  分页
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    public void listByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, InvocationTargetException, IllegalAccessException {
        UserDb userDb = (UserDb) request.getSession().getAttribute("userDb");
        //根据当前登陆的用户获取角色
        Integer role = userDb.getRole();
        String word = request.getParameter("word");
        String currentPage = request.getParameter("pageNum");
        String pageSize = request.getParameter("pageSize");
        PageTool<EvaluateDb> pageTool = null;
        if (role == 1) {
            pageTool = evaluateService.list(currentPage, pageSize, word, 1, userDb.getUid());
        } else {
            //管理员
            pageTool = evaluateService.list(currentPage, pageSize, word, 1, null);
        }
        String path = "problem?method=listByPage";
        if (word != null && word != "") {
            path += "&word=" + word;
        }
        //生成前端分页按钮
        String pagation = PaginationUtils.getPagation(pageTool.getTotalCount(), pageTool.getCurrentPage(), pageTool.getPageSize(), path);
        request.setAttribute("pagation", pagation);
        request.setAttribute("start", pageTool.getStartIndex());
        request.setAttribute("pList", pageTool.getRows());
        //根据role判断跳转的页面
        if (role == 1) {
            //普通用户
            request.getRequestDispatcher("user/myproblem.jsp").forward(request, response);
        } else {
            //管理员
            request.getRequestDispatcher("admin/admin_feedback.jsp").forward(request, response);
        }

    }
    /**
     * 添加问题
     * @param request
     * @param response
     */
    public void addProblem(HttpServletRequest request, HttpServletResponse response) throws Exception {
        EvaluateDb evaluateDb = new EvaluateDb();
        BeanUtils.populate(evaluateDb, request.getParameterMap());
        UserDb userDb = (UserDb) request.getSession().getAttribute("userDb");
        evaluateDb.setUid(userDb.getUid());
        evaluateDb.setTime(new Date());
        evaluateService.addProblem(evaluateDb);
        response.sendRedirect("problem?method=listByPage");
    }

    /**
     * 删除问题
     * @param request
     * @param response
     */
    public void delProblem(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String pid = request.getParameter("pid");
        evaluateService.delProblem(pid);
        response.sendRedirect("problem?method=listByPage");
    }

    public void listPage(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException {
        UserDb userDb = (UserDb) req.getSession().getAttribute("userDb");
        //根据当前登陆的用户获取角色
        String word = req.getParameter("word");
        String currentPage = req.getParameter("pageNum");
        String pageSize = req.getParameter("pageSize");
        PageTool<EvaluateDb> pageTool = null;
        pageTool = evaluateService.list(currentPage, pageSize, word, 1, null);
        String path = "problem?method=ListPage";
        if (word != null && word != "") {
            path += "&word=" + word;
        }
        //生成前端分页按钮
        String pagation = PaginationUtils.getPagation(pageTool.getTotalCount(), pageTool.getCurrentPage(), pageTool.getPageSize(), path);
        req.setAttribute("pagation", pagation);
        req.setAttribute("start", pageTool.getStartIndex());
        req.setAttribute("pList", pageTool.getRows());
        req.getRequestDispatcher("user/user_feedback.jsp").forward(req, resp);

    }
}
