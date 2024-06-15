package Dao;

import Utils.C3p0Tool;
import Utils.PageTool;
import entity.BookDb;
import org.apache.commons.dbutils.*;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDao {
    QueryRunner qr = new QueryRunner(C3p0Tool.getDataSource());
    //开启驼峰自动转换
    BeanProcessor bean = new GenerousBeanProcessor();
    RowProcessor processor = new BasicRowProcessor(bean);

    /**
     * 图书列表   分页
     *
     * @return
     */
    public PageTool<BookDb> list (String currentPage, String pageSize,String word){

        try {

            StringBuffer listSql = new StringBuffer("select b.* ,type_name ");
            StringBuffer countSql = new StringBuffer("select count(*) ");
            StringBuffer sql = new StringBuffer("from t_book b LEFT JOIN t_type t ON b.tid=t.tid ");
            if(word != null && !word.isEmpty()){
                sql.append(" where book_name like '%"+word+"%'");
                sql.append(" or type_name like '%"+word+"%'");
                sql.append(" or author like '%"+word+"%'");
                sql.append(" or press like '%"+word+"%'");
            }
            //获取总记录数
            Long total= qr.query(countSql.append(sql).toString(),new ScalarHandler<Long>());
            //初始化分页工具
            PageTool<BookDb> pageTools=new PageTool<BookDb>(total.intValue(),currentPage,pageSize);
            sql.append(" limit ?,? ");
            //查询当前页的数据

            List<BookDb> list= qr.query(listSql.append(sql).toString(),
                    new BeanListHandler<BookDb>(BookDb.class,processor),
                    pageTools.getStartIndex(),
                    pageTools.getPageSize());
            pageTools.setRows(list);
            return pageTools;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return new PageTool<BookDb>(0,currentPage,pageSize);
    }

    /**
     * 多条件查询
     * @return
     */
    public List<BookDb> list( String bookName,String bid){
        StringBuffer listSql = new StringBuffer("select * from t_book where 1=1 ");
        List<Object> params = new ArrayList<Object>();

        if(bookName!=null&&bookName!=""){
            listSql.append(" and book_name = ? ");
            params.add(bookName);
        }
        if(bid!=null&&bid!=""){
            listSql.append(" and bid = ? ");
            params.add(bid);
        }
        try {
            return qr.query(listSql.toString(),new BeanListHandler<BookDb>(BookDb.class,processor),params.toArray());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 添加图书
     * @param
     * @return
     */
    public Integer addBook(BookDb bookDb){
        String sql="insert into t_book (book_name,author,num,press,tid,times) values (?,?,?,?,?,0)";
        Object[] params = {bookDb.getBookName(), bookDb.getAuthor(),bookDb.getNum(),
                bookDb.getPress(),bookDb.getTid()};
        try {
            return qr.update(sql,params);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 修改图书
     */
    public Integer updateBook(BookDb book){
        String sql="update t_book set tid = ?, press= ?,num= ? where bid= ?";
        Object[] params = {book.getTid(),book.getPress(),book.getNum(),book.getBid()};
        try {
            return qr.update(sql,params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 删除图书
     */
    public int delBook(Integer bid){
        String sql = "delete from t_book where bid=?";
        Object[] params = {bid};
        try {
            return qr.update(sql,params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 改变库存
     * @param
     * @return
     */
    public Integer changeNum(BookDb book, Connection con)throws SQLException {
        QueryRunner qr = new QueryRunner();
        String sql = "update t_book set times = ?,num= ? where bid= ?";
        Object[] params = {book.getTimes(), book.getNum(), book.getBid()};
        return qr.update(con, sql, params);
    }
}
