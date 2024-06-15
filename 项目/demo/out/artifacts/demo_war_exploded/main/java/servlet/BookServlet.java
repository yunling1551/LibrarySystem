package servlet;

import Utils.PageTool;
import Utils.PaginationUtils;
import Utils.ResBean;
import com.google.gson.Gson;
import entity.BookDb;
import entity.TypeDb;
import entity.UserDb;
import org.apache.commons.beanutils.BeanUtils;
import service.BookService;
import service.TypeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/book")
public class BookServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private BookService bookService=new BookService();

    private TypeService typeService=new TypeService();

    /**
     * 图书列表  分页
     * @param req
     * @param resp
     */
    public void listByPage(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException {
        UserDb userDb= (UserDb) req.getSession().getAttribute("userDb");
        //根据当前用户获取角色
        Integer role= userDb.getRole();
        String word= req.getParameter("word");
        String currentPage= req.getParameter("pageNum");
        String pageSize= req.getParameter("pageSize");
        PageTool<BookDb> pageTools= bookService.listByPage(currentPage,pageSize,word);
        String path="book?method=listByPage";
        if(word!=null && word != ""){
            path += "&word="+word;
        }
        //生成前端分页按钮
        String pagation= PaginationUtils.getPagation(pageTools.getTotalCount(),
                pageTools.getCurrentPage(),
                pageTools.getPageSize(),
                path);
        List<TypeDb> typeList= typeService.list(null,null);
        req.setAttribute("pagation",pagation);
        req.setAttribute("typeList",typeList);
        req.setAttribute("word",word);
        req.setAttribute("bList", pageTools.getRows());
        //根据role判断跳转的页面
        if(role==1){
            //普通用户
            req.getRequestDispatcher("user/select.jsp").forward(req,resp);
        }else{
            //管理员
            req.getRequestDispatcher("admin/admin_book.jsp").forward(req,resp);
        }
    }

    /**
     * 图书名称校验
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void checkBook(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException {
        String bookName= req.getParameter("bookName");
        List<BookDb> list= bookService.list(bookName);
        ResBean resBean=new ResBean();
        if(list!=null&&list.size()>0){
            resBean.setCode(400);
            resBean.setMsg("书名不可用");
        }else {
            resBean.setCode(200);
            resBean.setMsg("书名可用");
        }
        Gson gson=new Gson();
        String json=gson.toJson(resBean);
        resp.getWriter().print(json);

    }

    /**
     * 添加图书
     */
    public void addBook(HttpServletRequest req, HttpServletResponse resp)  throws Exception{

        BookDb bookDb=new BookDb();
        BeanUtils.populate(bookDb,req.getParameterMap());
        bookService.addBook(bookDb);
        resp.sendRedirect("book?method=listByPage");
    }

    /**
     * 修改图书
     */
    public void updateBook(HttpServletRequest req, HttpServletResponse resp)  throws Exception {
        BookDb bookDb = new BookDb();
        BeanUtils.populate(bookDb,req.getParameterMap());
        bookService.updateBook(bookDb);
        resp.sendRedirect("book?method=listByPage");
    }
    /**
     * 删除图书
     */
    public void delBook(HttpServletRequest req, HttpServletResponse resp)  throws Exception {
        String bid= req.getParameter("bid");
        bookService.delBook(Integer.parseInt(bid));
        resp.sendRedirect("book?method=listByPage");
    }

    /**
     * 图书借阅
     */
    public void borrowBook(HttpServletRequest req, HttpServletResponse resp)  throws Exception {
        //获取要借阅的图书ID
        String bid = req.getParameter("bid");
        //获取当前用户信息
        UserDb userDb= (UserDb) req.getSession().getAttribute("userDb");
        bookService.borrowBook(userDb,bid);
        resp.sendRedirect("history?method=list");
    }
    /**
     * 归还图书
     */
    public void backBook(HttpServletRequest req, HttpServletResponse resp)  throws Exception {
        //获取要归还记录的ID
        String hid = req.getParameter("hid");
        bookService.backBook(hid);
        resp.sendRedirect("history?method=backList");
    }
}
