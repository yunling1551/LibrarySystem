package Dao;

import Utils.C3p0Tool;
import Utils.PageTool;
import entity.UserDb;
import org.apache.commons.dbutils.*;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 用户数据链接层
 */
public class UserDao {
    QueryRunner qr = new QueryRunner(C3p0Tool.getDataSource());
    //开启驼峰自动转换
    BeanProcessor bean = new GenerousBeanProcessor();
    RowProcessor processor = new BasicRowProcessor(bean);

    /**
     * 用户登录
     * @param account
     * @param password
     * @return
     */
    public UserDb login(String account, String password){
        String sql = "select * from t_user where account=? and password=?";
    Object[] params = {account, password};
        try {
            return qr.query(sql,new BeanHandler<UserDb>(UserDb.class,processor),params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除用户
     * @param uid
     */
    public int delUser(Integer uid){
        String sql = "delete from t_user where uid=?";
        Object[] params = {uid};
        try {
            return qr.update(sql,params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 添加用户
     * @param user
     * @return
     */
    public Integer addUser(UserDb user){
        String sql="insert into t_user (account,password,name,phone,times,lend_num,max_num,role) values(?,?,?,?,?,?,?,?)";
        Object[] params = {user.getAccount(),
                user.getPassword(),
                user.getName(),
                user.getPhone(),
                user.getTimes(),
                user.getLendNum(),
                user.getMaxNum(),
                user.getRole()};
        try {
            return qr.update(sql,params);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 用户列表   分页
     *
     * @return
     */
    public PageTool<UserDb> list (String currentPage, String pageSize){

        try {
            StringBuffer listSql = new StringBuffer("select * ");
            StringBuffer countSql = new StringBuffer("select count(*) ");
            StringBuffer sql = new StringBuffer("from t_user ");
            //获取总记录数
            Long total= qr.query(countSql.append(sql).toString(),new ScalarHandler<Long>());
            //初始化分页工具
            PageTool<UserDb> pageTools=new PageTool<UserDb>(total.intValue(),currentPage,pageSize);
            sql.append(" limit ?,? ");
            //查询当前页的数据

           List<UserDb> list= qr.query(listSql.append(sql).toString(),
                   new BeanListHandler<UserDb>(UserDb.class,processor),
                   pageTools.getStartIndex(),
                   pageTools.getPageSize());
           pageTools.setRows(list);
           return pageTools;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return new PageTool<UserDb>(0,currentPage,pageSize);
    }

    /**
     * 管理员修改用户信息
     * @param user
     * @return
     */
    public Integer updateUser(UserDb user){
        String sql="update t_user set phone=?,lend_num=?,max_num=? where uid=?";
        Object[] params = {user.getPhone(),user.getLendNum(),user.getMaxNum(),user.getUid()};
        try {
            return qr.update(sql,params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
/**
 * 校验
 */
    public List<UserDb> getList(UserDb user){
        String sql="select * from t_user where account=? ";
        Object[] params={user.getAccount()};
        try{
            return qr.query(sql,new BeanListHandler<UserDb>(UserDb.class,processor),params);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 修改图书借阅信息
     * @param user
     * @return
     * @throws SQLException
     */
    public Integer updateNum(UserDb user, Connection con) throws SQLException {
        QueryRunner qr = new QueryRunner();
        String sql="update t_user set times=?,max_num=? where uid=?";
        Object[] params = {user.getTimes(),user.getMaxNum(),user.getUid()};
        return qr.update(con,sql,params);

    }

}
