package servlet;

import Utils.PageTool;
import Utils.PaginationUtils;
import Utils.ResBean;
import com.google.gson.Gson;
import entity.TypeDb;
import service.TypeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 图书分类
 */
@WebServlet("/type")
public class TypeServlet extends BaseServlet{
    private static final long serialVersionUID = 1L;
    private TypeService typeService=new TypeService();

    public void listPage(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException {
        String currentPage= req.getParameter("pageNum");
        String pageSize= req.getParameter("pageSize");
        PageTool<TypeDb> pageTools= typeService.listPage(currentPage,pageSize);
        //生成前端分页按钮
        String pagation= PaginationUtils.getPagation(pageTools.getTotalCount(),
                pageTools.getCurrentPage(),
                pageTools.getPageSize(),
                "type?method=listPage");
        req.setAttribute("pagation",pagation);
        req.setAttribute("tList", pageTools.getRows());
        req.getRequestDispatcher("admin/admin_booktype.jsp").forward(req,resp);
    }

    /**
     * 类型名称校验
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void checkType(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException {
        String typeName= req.getParameter("typeName");
        List<TypeDb> list= typeService.list(null,typeName);
        ResBean resBean=new ResBean();
        if(list!=null&&list.size()>0){
            resBean.setCode(400);
            resBean.setMsg("类别不可用");
        }else {
            resBean.setCode(200);
            resBean.setMsg("类别可用");
        }
        Gson gson=new Gson();
        String json=gson.toJson(resBean);
        resp.getWriter().print(json);

    }

    /**
     * 类型添加
     * @param req
     * @param resp

     */
    public void addType(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException {
        String typeName= req.getParameter("typeName");
        typeService.addType(typeName);
        req.getRequestDispatcher("type?method=listPage").forward(req,resp);
    }

    /**
     * 修改图书分类
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void updateType(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException {
        String tid= req.getParameter("tid");
        String typeName= req.getParameter("typeName");
        TypeDb typeDb=new TypeDb();
        typeDb.setTid(Integer.parseInt(tid));
        typeDb.setTypeName(typeName);
        typeService.updateType(typeDb);
        req.getRequestDispatcher("type?method=listPage").forward(req,resp);
    }

    /**
     * 删除
     */
    public void delType(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException {
        String tid= req.getParameter("tid");
        typeService.delType(Integer.parseInt(tid));
        req.getRequestDispatcher("type?method=listPage").forward(req,resp);
    }
}
