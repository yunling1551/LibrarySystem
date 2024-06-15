package servlet;


import Utils.DateUtils;
import Utils.PageTool;
import Utils.PaginationUtils;
import entity.HistoryDb;
import entity.UserDb;
import service.HistoryService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;


/**
 * 图书借阅历史记录
 */
@WebServlet("/history")
public class HistoryServlet extends BaseServlet{
    private static final long serialVersionUID = 1L;

    private HistoryService historyService=new HistoryService();

    /**
     * 已被归还的借阅记录查询
     */
    public void backList(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException {
        UserDb userDb= (UserDb) req.getSession().getAttribute("userDb");
        //根据当前用户获取角色
        Integer role= userDb.getRole();
        String currentPage= req.getParameter("pageNum");
        String pageSize= req.getParameter("pageSize");
        //儒棍当前用户为普通用户查自己，管理员查所有
        PageTool<HistoryDb> pageTools=null;
        if(role==1)
        {
            pageTools = historyService.listByPage(currentPage,pageSize,userDb.getUid(),2);
        }else{
            pageTools = historyService.listByPage(currentPage,pageSize,null,2);
        }


        String path="history?method=backList";
        //生成前端分页按钮
        String pagation= PaginationUtils.getPagation(pageTools.getTotalCount(),
                pageTools.getCurrentPage(),
                pageTools.getPageSize(),
                path);
        req.setAttribute("pagation",pagation);
        req.setAttribute("hList", pageTools.getRows());
        //根据role判断跳转的页面
        if(role==1){
            //普通用户
            req.getRequestDispatcher("user/history.jsp").forward(req,resp);
        }else{
            //管理员
            req.getRequestDispatcher("admin/admin_history.jsp").forward(req,resp);
        }
    }

    public void list(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException {
        UserDb userDb= (UserDb) req.getSession().getAttribute("userDb");
        //根据当前用户获取角色
        Integer role= userDb.getRole();
        String currentPage= req.getParameter("pageNum");
        String pageSize= req.getParameter("pageSize");
        //儒棍当前用户为普通用户查自己，管理员查所有
        PageTool<HistoryDb> pageTools=null;
        if(role==1)
        {
            pageTools = historyService.listByPage(currentPage,pageSize,userDb.getUid(),1);
        }else{
            pageTools = historyService.listByPage(currentPage,pageSize,null,1);
        }


        String path="history?method=list";
        //生成前端分页按钮
        String pagation= PaginationUtils.getPagation(pageTools.getTotalCount(),
                pageTools.getCurrentPage(),
                pageTools.getPageSize(),
                path);
        req.setAttribute("pagation",pagation);
        req.setAttribute("hList", pageTools.getRows());
        //根据role判断跳转的页面
        if(role==1){
            //普通用户
            req.getRequestDispatcher("user/borrow.jsp").forward(req,resp);
        }else{
            //管理员
            req.getRequestDispatcher("admin/admin_borrow.jsp").forward(req,resp);
        }
    }

    /**
     *图书归还延期
     */
    public void delay(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        String hid= req.getParameter("hid");
        String endTime= req.getParameter("endtime");

        HistoryDb historyDb=new HistoryDb();
        historyDb.setHid(Integer.parseInt(hid) );
        historyDb.setEndTime(DateUtils.stringToDate(endTime));
        historyService.updateHistory(historyDb);
        req.getRequestDispatcher("history?method=list").forward(req,resp);
    }
}
