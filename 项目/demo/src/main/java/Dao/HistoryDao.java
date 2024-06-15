package Dao;

import Utils.C3p0Tool;
import Utils.PageTool;
import entity.HistoryDb;
import org.apache.commons.dbutils.*;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HistoryDao {
    QueryRunner queryRunner = new QueryRunner(C3p0Tool.getDataSource());
    //开启驼峰自动转换
    BeanProcessor bean = new GenerousBeanProcessor();
    RowProcessor processor = new BasicRowProcessor(bean);
    /**
     * 添加历史记录
     * @param
     * @return
     */

    public Integer addHistory(HistoryDb historyDb, Connection con) throws SQLException {
        QueryRunner qr = new QueryRunner();
        String sql="insert into t_history (uid,name,account,bid,book_name,begin_time,end_time,status) values (?,?,?,?,?,?,?,?)";
        Object[] params = {historyDb.getUid(),historyDb.getName(),historyDb.getAccount(),historyDb.getBid(),
                historyDb.getBookName(),historyDb.getBeginTime(),historyDb.getEndTime(),historyDb.getStatus()};
        return qr.update(con,sql, params);

    }

    /**
     * 借阅历史查询
     * @return
     */
    public PageTool<HistoryDb> listByPage (String currentPage, String pageSize, Integer uid,Integer status){

        try {

            StringBuffer listSql = new StringBuffer("select *");
            StringBuffer countSql = new StringBuffer("select count(*)");
            StringBuffer sql = new StringBuffer("from t_history where 1 = 1");
            List<Object> params = new ArrayList<>();
            if(uid != null ){
                sql.append(" and uid = ? ");
                params.add(uid);
            }
            if(status != null ){
                sql.append(" and status = ? ");
                params.add(status);
            }
            //获取总记录数
            Long total= queryRunner.query(countSql.append(sql).toString(),new ScalarHandler<Long>(),params.toArray());
            //初始化分页工具
            PageTool<HistoryDb> pageTools=new PageTool<HistoryDb>(total.intValue(),currentPage,pageSize);
            sql.append("order by begin_time desc limit ?,? ");
            params.add(pageTools.getStartIndex());
            params.add(pageTools.getPageSize());
            //查询当前页的数据
            List<HistoryDb> list= queryRunner.query(listSql.append(sql).toString(),
                    new BeanListHandler<HistoryDb>(HistoryDb.class,processor),params.toArray());
            pageTools.setRows(list);
            return pageTools;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return new PageTool<HistoryDb>(0,currentPage,pageSize);
    }

    /**
     * 无分页查询
     * @return
     */
    public List<HistoryDb> list(String hid){
        StringBuffer listSql = new StringBuffer("select * from t_history where 1 = 1 ");
        List<Object> params = new ArrayList<>();
        if(hid != null && hid != ""){
            listSql.append("and hid = ? ");
            params.add(hid);
        }
        try {
            return queryRunner.query(listSql.toString(),new BeanListHandler<HistoryDb>(HistoryDb.class,processor),params.toArray());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 修改图书历史记录
     */
    public Integer updateHistory(HistoryDb historyDb, Connection con) throws SQLException {
        QueryRunner qr = new QueryRunner();
        String sql="update t_history set status = ? where hid= ?";
        Object[] params = {historyDb.getStatus(),historyDb.getHid()};
        return qr.update(con,sql,params);

    }

    public Integer updateHistory(HistoryDb historyDb) throws SQLException {
        String sql="update t_history set end_time = ? where hid= ?";
        Object[] params = {historyDb.getEndTime(),historyDb.getHid()};
        return queryRunner.update(sql,params);

    }
}
