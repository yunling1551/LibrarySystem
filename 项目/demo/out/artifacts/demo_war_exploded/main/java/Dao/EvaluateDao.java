package Dao;

import Utils.C3p0Tool;
import Utils.PageTool;
import entity.EvaluateDb;
import org.apache.commons.dbutils.*;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EvaluateDao {
    QueryRunner qr = new QueryRunner(C3p0Tool.getDataSource());
    //开启驼峰自动转换
    BeanProcessor bean = new GenerousBeanProcessor();
    RowProcessor processor = new BasicRowProcessor(bean);

    /**
     * 列表 分页
     * @return
     */
    public PageTool<EvaluateDb> list(String currentPage, String pageSize, String word, Integer order, Integer uid){
        try {
            StringBuffer listSql = new StringBuffer("select p.*,name,account");
            StringBuffer countSql = new StringBuffer("select count(*) ");
            StringBuffer sql = new StringBuffer(" from t_evaluate p left join t_user u on p.uid = u.uid where 1 = 1");
            List<Object> params = new ArrayList<>();
            if (uid != null) {
                sql.append(" and p.uid = ?");
                params.add(uid);
            }
            if (word != null && !word.isEmpty()) {
                sql.append(" and title like '%" + word + "%'");
                sql.append(" or book like '%" + word + "%'");
                sql.append(" or content like '%" + word + "%'");
                sql.append(" or name like '%" + word + "%'");
                sql.append(" or account like '%" + word + "%'");
            }

            if (order != null && order == 1) {
                sql.append(" order by time desc");
            }
            //获取总记录数
            Long total = qr.query(countSql.append(sql).toString(), new ScalarHandler<Long>(),params.toArray());
            //初始化分页工具
            PageTool<EvaluateDb> pageTools = new PageTool<EvaluateDb>(total.intValue(), currentPage, pageSize);
            sql.append(" limit ?,?");
            params.add(pageTools.getStartIndex());
            params.add(pageTools.getPageSize());
            //当前页的数据
            List<EvaluateDb> list = qr.query(listSql.append(sql).toString(), new BeanListHandler<EvaluateDb>(EvaluateDb.class, processor),params.toArray());
            pageTools.setRows(list);
            //System.out.println(pageTools);
            return pageTools;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new PageTool<EvaluateDb>(0, currentPage, pageSize);
    }

    /**
     * 添加问题
     * @param
     * @return
     */
    public Integer addProblem(EvaluateDb evaluateDb) {
        String sql = "insert into t_evaluate (uid,title,book,content,time) values (?,?,?,?,?)";
        Object[] params = {evaluateDb.getUid(), evaluateDb.getTitle(), evaluateDb.getBook(), evaluateDb.getContent(), evaluateDb.getTime()};
        try {
            return qr.update(sql, params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除问题
     * @return
     */
    public Integer delProblem(String pid) {
        String sql = "delete from t_evaluate where pid = ?";
        Object[] params = {pid};
        try {
            return qr.update(sql, params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
