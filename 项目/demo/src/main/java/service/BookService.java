package service;

import Dao.BookDao;
import Dao.HistoryDao;
import Dao.UserDao;
import Utils.C3p0Tool;
import Utils.DateUtils;
import Utils.MyException;
import Utils.PageTool;
import entity.BookDb;
import entity.HistoryDb;
import entity.UserDb;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;


public class BookService {

    private BookDao bookDao = new BookDao();
    private HistoryDao historyDao = new HistoryDao();
    private UserDao userDao = new UserDao();
    /**
     * 图书分页查询
     */
    public PageTool<BookDb> listByPage (String currentPage, String pageSize,String word){
        return bookDao.list(currentPage,pageSize,word);
    }

    public List<BookDb> list(String bookName){return bookDao.list(bookName,null);}

    public Integer addBook(BookDb bookDb){return bookDao.addBook(bookDb);}

    public Integer updateBook(BookDb book){return bookDao.updateBook(book);}

    public int delBook(Integer bid){return bookDao.delBook(bid);}

    /**
     * 用户借阅图书
     * @param userDb
     * @param bid
     */
    public void borrowBook(UserDb userDb, String bid) {
        //数据库链接
        Connection con= C3p0Tool.getConnection();
        try{
            //设置事务不自动提交
            con.setAutoCommit(false);
            //根据bid获取完整的图书信息
            List<BookDb> list = bookDao.list(null,bid);
            BookDb book = list.get(0);
            //保证数据同步更新
            userDb= userDao.getList(userDb).get(0);


            //t_history 图书借阅历史记录
            HistoryDb history = new HistoryDb();
            history.setUid(userDb.getUid());
            history.setName(userDb.getName());
            history.setAccount(userDb.getAccount());
            history.setBid(book.getBid());
            history.setBookName(book.getBookName());
            Date d= new Date();
            history.setBeginTime(d);
            history.setEndTime(DateUtils.dateAdd(d,userDb.getLendNum()));//还书时间=结束时间+lend_num
            history.setStatus(1);
            historyDao.addHistory(history,con);
            //t_book改变图书的库存  book.num--  book.times++
            Integer num= book.getNum();
            //库存判断
            if(num<=0){
               throw new MyException("库存不足") ;
            }
            book.setNum(--num);
            Integer times= book.getTimes();
            book.setTimes(++times);

            bookDao.changeNum(book,con);
            //t_user改变用户信息  user.times++  Max_num--
            userDb.setTimes(userDb.getTimes()+1);
            userDb.setMaxNum(userDb.getMaxNum()-1);
            if(userDb.getMaxNum()<=0){
                throw new MyException("借阅次数已满") ;
            }
            userDao.updateNum(userDb,con);
            //事务提交
            con.commit();
        }catch (Exception e){

            //事务回滚
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            //判断自定义异常
            if(e instanceof MyException){
                throw new MyException( e.getMessage());
            }else{
                e.printStackTrace();
                throw new MyException("借阅失败");
            }
        }
        //throw new MyException("我的异常");
    }

    /**
     * 还书
     */
    public void backBook(String hid){
        //数据库链接
        Connection con= C3p0Tool.getConnection();
        try{
            //设置事务不自动提交
            con.setAutoCommit(false);
            //根据hid获取historyDB,修改status为2
            HistoryDb historyDb= historyDao.list(hid).get(0);
            historyDb.setStatus(2);
            historyDao.updateHistory(historyDb,con);

            //根据historyDB获取图书bid
            Integer bid= historyDb.getBid();
            //根据bid获取图书信息，修改库存+1
            BookDb bookDb=bookDao.list(historyDb.getBookName(),bid+"").get(0);
            bookDb.setNum(bookDb.getNum()+1);
            bookDao.changeNum(bookDb,con);

            //根据historyDB获取用户account
            String account= historyDb.getAccount();
            //根据uid获取用户信息，修改max_num+1
            UserDb userDb=new UserDb();
            userDb.setAccount(account);
            userDb= userDao.getList(userDb).get(0);
            userDb.setMaxNum(userDb.getMaxNum()+1);
            userDao.updateNum(userDb,con);

            //事务提交
            con.commit();
        }catch (Exception e){

            //事务回滚
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            //判断自定义异常
            if(e instanceof MyException){
                throw new MyException( e.getMessage());
            }else{
                e.printStackTrace();
                throw new MyException("还书失败");
            }
        }
        //throw new MyException("我的异常");
    }
}
